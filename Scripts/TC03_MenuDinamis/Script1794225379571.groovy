import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import internal.GlobalVariable as GlobalVariable

// 1. Buka browser dan login ke CURA
WebUI.openBrowser('')
WebUI.navigateToUrl(GlobalVariable.baseUrl)
CustomKeywords.'com.cura.helpers.AuthKeywords.loginToCura'('John Doe', 'ThisIsNotAPassword')

// Objek yang dipakai berulang disimpan di variabel
def toggle = findTestObject('Object Repository/Page_Home/btn_MenuToggle')

// 2. Klik hamburger menu (tunggu sampai bisa diklik)
WebUI.waitForElementClickable(toggle, 10)
WebUI.click(toggle)

// 3. Klik menu 'History' menggunakan Objek Dinamis
def menuHistory = findTestObject('Object Repository/Page_Home/btn_MenuDinamis', [('namaMenu') : 'History'])
WebUI.waitForElementClickable(menuHistory, 10)
WebUI.click(menuHistory)
WebUI.waitForPageLoad(10)

// 4. Buka kembali hamburger menu
WebUI.waitForElementClickable(toggle, 10)
WebUI.click(toggle)

// 5. Klik menu 'Profile' menggunakan Objek Dinamis yang sama
def menuProfile = findTestObject('Object Repository/Page_Home/btn_MenuDinamis', [('namaMenu') : 'Profile'])
WebUI.waitForElementClickable(menuProfile, 10)
WebUI.click(menuProfile)
WebUI.waitForPageLoad(10)

WebUI.closeBrowser()