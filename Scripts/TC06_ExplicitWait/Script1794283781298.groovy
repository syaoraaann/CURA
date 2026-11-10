import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// ===== Helper: bikin Test Object dari XPath =====
def makeTO(String name, String xpath) {
	TestObject to = new TestObject(name)
	to.addProperty('xpath', ConditionType.EQUALS, xpath)
	return to
}

def btnStart   = makeTO('btnStart', "//div[@id='start']/button")
def loading    = makeTO('loading', "//div[@id='loading']")
def finishText = makeTO('finishText', "//div[@id='finish']/h4")

WebUI.openBrowser('')

// ============================================================
// DEMO A: Contoh 1 (elemen SUDAH ada di DOM tapi tersembunyi)
// ============================================================
WebUI.navigateToUrl('https://the-internet.herokuapp.com/dynamic_loading/1')

WebUI.waitForElementClickable(btnStart, 10)
WebUI.click(btnStart)
long t0 = System.currentTimeMillis()

// A1. Timeout sengaja terlalu singkat (2 detik). OPTIONAL = tidak menggagalkan test, cuma return false
boolean muncul = WebUI.waitForElementVisible(finishText, 2, FailureHandling.OPTIONAL)
println("A1. Muncul dalam 2 detik? " + muncul)

// A2. Timeout cukup (15 detik). Lanjut begitu elemen terlihat
WebUI.waitForElementVisible(finishText, 15)
long detik = (System.currentTimeMillis() - t0) / 1000
println("A2. Teks: '" + WebUI.getText(finishText) + "' (total tunggu sekitar " + detik + " detik)")

// A3. Tunggu loading hilang
WebUI.waitForElementNotVisible(loading, 5)
println("A3. Loading sudah hilang")

// ============================================================
// DEMO B: JEBAKAN, waitForElementPresent di elemen tersembunyi
// ============================================================
WebUI.navigateToUrl('https://the-internet.herokuapp.com/dynamic_loading/1')
WebUI.waitForElementClickable(btnStart, 10)
WebUI.click(btnStart)

WebUI.waitForElementPresent(finishText, 5)        // langsung lolos, elemennya sudah ada di DOM
println("B1. Teks (pakai Present): '" + WebUI.getText(finishText) + "'")   // kosong, karena belum terlihat

WebUI.waitForElementVisible(finishText, 15)       // yang benar
println("B2. Teks (pakai Visible): '" + WebUI.getText(finishText) + "'")

// ============================================================
// DEMO C: Contoh 2 (elemen BARU DIBUAT setelah loading)
// ============================================================
WebUI.navigateToUrl('https://the-internet.herokuapp.com/dynamic_loading/2')
WebUI.waitForElementClickable(btnStart, 10)
WebUI.click(btnStart)

WebUI.waitForElementVisible(finishText, 15)
println("C1. Teks: '" + WebUI.getText(finishText) + "'")
WebUI.verifyElementText(finishText, 'Hello World!')

WebUI.closeBrowser()