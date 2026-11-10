import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By

// ===== 1. Buka halaman tabel =====
WebUI.openBrowser('')
WebUI.navigateToUrl('https://the-internet.herokuapp.com/tables')
WebUI.waitForElementVisible(findTestObject('Object Repository/Page_Table/tbl_Cell', [('row') : '1', ('col') : '1']), 10)

// ===== 2. Ambil teks satu sel (baris 1, kolom 3 = Email) =====
String email = WebUI.getText(findTestObject('Object Repository/Page_Table/tbl_Cell', [('row') : '1', ('col') : '3']))
println("Email baris 1: " + email)
WebUI.verifyMatch(email, 'jsmith@gmail.com', false)

// ===== 3. Loop semua baris: tampilkan Last Name dan Due =====
def rows = DriverFactory.getWebDriver().findElements(By.xpath("//table[@id='table1']/tbody/tr"))
println("Jumlah baris: " + rows.size())

for (int i = 1; i <= rows.size(); i++) {
    String lastName = WebUI.getText(findTestObject('Object Repository/Page_Table/tbl_Cell', [('row') : i.toString(), ('col') : '1']))
    String due = WebUI.getText(findTestObject('Object Repository/Page_Table/tbl_Cell', [('row') : i.toString(), ('col') : '4']))
    println("Baris ${i}: ${lastName} -> ${due}")
}

// ===== 4. Cari berdasarkan isi (bukan posisi baris) =====
String emailDoe = WebUI.getText(findTestObject('Object Repository/Page_Table/tbl_CellByName', [('lastName') : 'Doe', ('col') : '3']))
println("Email Doe: " + emailDoe)
WebUI.verifyMatch(emailDoe, 'jdoe@hotmail.com', false)

String dueBach = WebUI.getText(findTestObject('Object Repository/Page_Table/tbl_CellByName', [('lastName') : 'Bach', ('col') : '4']))
println("Due Bach: " + dueBach)
WebUI.verifyMatch(dueBach, '$51.00', false)

// ===== 5. Klik link Action di baris tertentu =====
WebUI.waitForElementClickable(findTestObject('Object Repository/Page_Table/tbl_ActionLink', [('lastName') : 'Bach', ('action') : 'edit']), 10)
WebUI.click(findTestObject('Object Repository/Page_Table/tbl_ActionLink', [('lastName') : 'Bach', ('action') : 'edit']))
WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/Page_Table/tbl_ActionLink', [('lastName') : 'Conway', ('action') : 'delete']))
WebUI.delay(1)

// ===== 6. Tutup browser =====
WebUI.closeBrowser()