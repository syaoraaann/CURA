import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

try {
	WebUI.navigateToUrl('https://the-internet.herokuapp.com/tables')

	// Kondisi awal (belum diurutkan)
	println("Urutan awal Last Name: " + CustomKeywords.'com.table.helpers.TableKeywords.getColumnValues'(1))

	// ===== Kolom 1 (Last Name), teks =====
	CustomKeywords.'com.table.helpers.TableKeywords.sortByColumn'(1)
	CustomKeywords.'com.table.helpers.TableKeywords.verifyColumnSorted'(1, true, false)    // naik

	CustomKeywords.'com.table.helpers.TableKeywords.sortByColumn'(1)
	CustomKeywords.'com.table.helpers.TableKeywords.verifyColumnSorted'(1, false, false)   // turun

	// ===== Kolom 4 (Due), angka =====
	CustomKeywords.'com.table.helpers.TableKeywords.sortByColumn'(4)
	CustomKeywords.'com.table.helpers.TableKeywords.verifyColumnSorted'(4, true, true)     // naik

	CustomKeywords.'com.table.helpers.TableKeywords.sortByColumn'(4)
	CustomKeywords.'com.table.helpers.TableKeywords.verifyColumnSorted'(4, false, true)    // turun
} finally {
	WebUI.closeBrowser()
}