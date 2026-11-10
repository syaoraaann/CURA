import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser('')
WebUI.navigateToUrl(GlobalVariable.baseUrl)

// Tambahkan 2 baris ini untuk mengecek nilai di Console Katalon
println "=== DATA USERNAME EXCEL: " + username
println "=== DATA PASSWORD EXCEL: " + password

CustomKeywords.'com.cura.helpers.AuthKeywords.loginToCura'(username, password)

WebUI.delay(1)
WebUI.closeBrowser()