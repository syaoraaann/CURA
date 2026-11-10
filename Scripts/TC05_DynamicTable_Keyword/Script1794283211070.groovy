import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.navigateToUrl('https://the-internet.herokuapp.com/tables')

// Jumlah baris
int total = CustomKeywords.'com.table.helpers.TableKeywords.getRowCount'()
println("Jumlah baris: " + total)
WebUI.verifyEqual(total, 4)

// Ambil teks sel
String emailDoe = CustomKeywords.'com.table.helpers.TableKeywords.getCellText'('Doe', 3)
println("Email Doe: " + emailDoe)

// Verifikasi beberapa sel
CustomKeywords.'com.table.helpers.TableKeywords.verifyCellText'('Smith', 3, 'jsmith@gmail.com')
CustomKeywords.'com.table.helpers.TableKeywords.verifyCellText'('Bach', 4, '$51.00')
CustomKeywords.'com.table.helpers.TableKeywords.verifyCellText'('Conway', 2, 'Tim')

// Klik link Action
CustomKeywords.'com.table.helpers.TableKeywords.clickAction'('Bach', 'edit')
CustomKeywords.'com.table.helpers.TableKeywords.clickAction'('Conway', 'delete')

WebUI.closeBrowser()