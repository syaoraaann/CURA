import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By

String url = 'https://datatables.net/examples/basic_init/zero_configuration.html'

// Selector (sudah sesuai HTML yang kamu kirim, tidak perlu diubah)
String xpathBaris = "//table[@id='example']/tbody/tr/td[1]"
String xpathNext  = "//button[contains(@class,'dt-paging-button') and contains(@class,'next')]"
String xpathAngka = "//nav[@aria-label='pagination']//button[not(contains(@class,'first')) and not(contains(@class,'previous')) and not(contains(@class,'next')) and not(contains(@class,'last'))]"
String xpathInfo  = "//*[contains(text(),'Showing') and contains(text(),'entries')]"

TestObject tabel = new TestObject('tabel')
tabel.addProperty('xpath', ConditionType.EQUALS, xpathBaris)

WebUI.openBrowser('')

try {
	WebUI.navigateToUrl(url)
	WebUI.waitForElementVisible(tabel, 15)
	def driver = DriverFactory.getWebDriver()

	List<String> semuaNama = []
	List<Integer> ukuranHalaman = []
	int halaman = 1

	// ===== 1. Kumpulkan data dari semua halaman =====
	while (halaman <= 50) {   // batas pengaman supaya tidak looping selamanya
		def sel = driver.findElements(By.xpath(xpathBaris))
		sel.each { semuaNama << it.getText().trim() }
		ukuranHalaman << sel.size()
		println("Halaman ${halaman}: ${sel.size()} baris")

		def next = driver.findElement(By.xpath(xpathNext))
		String kelas = next.getAttribute('class') ?: ''
		if (kelas.contains('disabled') || next.getAttribute('disabled') != null) {
			break   // tombol Next sudah nonaktif = halaman terakhir
		}
		next.click()
		halaman++
	}

	println("Total halaman dikunjungi: " + halaman)
	println("Total baris terkumpul: " + semuaNama.size())

	// ===== 2. Jumlah halaman harus sama dengan jumlah tombol nomor di layar =====
	int tombolAngka = driver.findElements(By.xpath(xpathAngka)).size()
	println("Jumlah tombol nomor halaman: " + tombolAngka)
	WebUI.verifyEqual(halaman, tombolAngka)

	// ===== 3. Semua halaman (kecuali terakhir) harus berisi 10 baris =====
	ukuranHalaman.eachWithIndex { jumlah, i ->
		if (i < ukuranHalaman.size() - 1) {
			WebUI.verifyEqual(jumlah, 10)
		}
	}
	int terakhir = ukuranHalaman[-1]
	WebUI.verifyGreaterThan(terakhir, 0)
	WebUI.verifyLessThanOrEqual(terakhir, 10)

	// ===== 4. Tidak boleh ada data ganda antar halaman =====
	WebUI.verifyEqual(semuaNama.unique(false).size(), semuaNama.size())

	// ===== 5. Opsional: cocokkan dengan teks "of N entries" kalau elemennya ketemu =====
	def infoList = driver.findElements(By.xpath(xpathInfo))
	if (infoList.size() > 0) {
		String info = infoList[0].getText()
		def cocok = (info =~ /of ([\d,]+) entries/)
		if (cocok.find()) {
			int totalInfo = cocok.group(1).replace(',', '').toInteger()
			println("Teks info: " + info)
			WebUI.verifyEqual(semuaNama.size(), totalInfo)
		}
	} else {
		println("Info: teks 'Showing ... entries' tidak ditemukan, verifikasi nomor 5 dilewati.")
	}
} finally {
	WebUI.closeBrowser()
}