import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.context.TestCaseContext

class TL_Common {

	@BeforeTestCase
	def sebelumTestCase(TestCaseContext testCaseContext) {
		println("=== MULAI : " + testCaseContext.getTestCaseId())
	}

	@AfterTestCase
	def sesudahTestCase(TestCaseContext testCaseContext) {
		println("=== SELESAI: " + testCaseContext.getTestCaseId() + " -> " + testCaseContext.getTestCaseStatus())
	}
}