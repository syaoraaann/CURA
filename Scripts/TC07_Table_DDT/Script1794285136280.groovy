import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.navigateToUrl('https://the-internet.herokuapp.com/tables')

// Ambil data dari Data File
def data = findTestData('Data Files/TD_TableData')
int total = data.getRowNumbers()
println("Jumlah data uji: " + total)

// Ulangi untuk setiap baris data (indeks Katalon dimulai dari 1)
for (int i = 1; i <= total; i++) {
	String lastName = data.getValue('lastName', i)
	int col         = data.getValue('col', i).toInteger()
	String expected = data.getValue('expected', i)

	println("Data ke-${i}: ${lastName}, kolom ${col}, ekspektasi ${expected}")
	CustomKeywords.'com.table.helpers.TableKeywords.verifyCellText'(lastName, col, expected)
}

WebUI.closeBrowser()