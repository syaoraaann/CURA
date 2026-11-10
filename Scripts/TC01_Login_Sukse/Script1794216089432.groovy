import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

// 1. Buka browser menggunakan URL dari Execution Profile
WebUI.openBrowser('')
WebUI.navigateToUrl(GlobalVariable.baseUrl)

// 2. Panggil Keyword login menggunakan kredensial dari GlobalVariable
CustomKeywords.'com.cura.helpers.AuthKeywords.loginToCura'(
    GlobalVariable.validUsername, 
    GlobalVariable.validPassword
)

// 3. Verifikasi & Tutup
WebUI.delay(2)
WebUI.closeBrowser()