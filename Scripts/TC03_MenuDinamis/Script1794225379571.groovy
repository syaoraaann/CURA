import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import internal.GlobalVariable as GlobalVariable

// Pengecekan awal: gagal cepat dengan pesan yang jelas kalau profile belum benar
assert GlobalVariable.baseUrl : 'GlobalVariable.baseUrl kosong. Cek nilai baseUrl di profile yang dipakai (default/Staging).'

WebUI.openBrowser('')

try {
	// 1. Buka halaman dan login ke CURA
	WebUI.navigateToUrl(GlobalVariable.baseUrl)
	CustomKeywords.'com.cura.helpers.AuthKeywords.loginToCura'('John Doe', 'ThisIsNotAPassword')

	def toggle = findTestObject('Object Repository/Page_Home/btn_MenuToggle')

	// 2. Klik hamburger menu
	WebUI.waitForElementClickable(toggle, 10)
	WebUI.click(toggle)

	// 3. Klik menu 'History' (objek dinamis)
	def menuHistory = findTestObject('Object Repository/Page_Home/btn_MenuDinamis', [('namaMenu') : 'History'])
	WebUI.waitForElementClickable(menuHistory, 10)
	WebUI.click(menuHistory)
	WebUI.waitForPageLoad(10)

	// 4. Buka kembali hamburger menu
	WebUI.waitForElementClickable(toggle, 10)
	WebUI.click(toggle)

	// 5. Klik menu 'Profile' (objek dinamis yang sama)
	def menuProfile = findTestObject('Object Repository/Page_Home/btn_MenuDinamis', [('namaMenu') : 'Profile'])
	WebUI.waitForElementClickable(menuProfile, 10)
	WebUI.click(menuProfile)
	WebUI.waitForPageLoad(10)
} finally {
	WebUI.closeBrowser()
}