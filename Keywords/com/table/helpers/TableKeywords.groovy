package com.table.helpers

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class TableKeywords {

	/**
	 * Menghitung jumlah baris di tabel.
	 */
	@Keyword
	def getRowCount() {
		def rows = DriverFactory.getWebDriver().findElements(By.xpath("//table[@id='table1']/tbody/tr"))
		return rows.size()
	}

	/**
	 * Mengambil teks sel berdasarkan Last Name (isi baris) dan nomor kolom.
	 */
	@Keyword
	def getCellText(String lastName, int col) {
		TestObject cell = findTestObject('Object Repository/Page_Table/tbl_CellByName',
				[('lastName') : lastName, ('col') : col.toString()])
		return WebUI.getText(cell)
	}

	/**
	 * Memverifikasi teks sel sama dengan nilai yang diharapkan.
	 */
	@Keyword
	def verifyCellText(String lastName, int col, String expected) {
		String actual = getCellText(lastName, col)
		println("Verifikasi ${lastName} kolom ${col}: aktual='${actual}', ekspektasi='${expected}'")
		WebUI.verifyMatch(actual, expected, false)
	}

	/**
	 * Klik link Action (edit/delete) pada baris dengan Last Name tertentu.
	 */
	@Keyword
	def clickAction(String lastName, String action) {
		TestObject link = findTestObject('Object Repository/Page_Table/tbl_ActionLink',
				[('lastName') : lastName, ('action') : action])
		WebUI.waitForElementClickable(link, 10)
		WebUI.click(link)
	}
}