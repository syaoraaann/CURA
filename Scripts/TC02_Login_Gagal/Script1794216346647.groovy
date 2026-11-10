import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser('')
WebUI.navigateToUrl(GlobalVariable.baseUrl)

// Menggunakan variabel lokal invalidUser dan invalidPass
CustomKeywords.'com.cura.helpers.AuthKeywords.loginToCura'(invalidUser, invalidPass)

WebUI.closeBrowser()