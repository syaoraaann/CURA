package com.table.helpers

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

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
	/**
	 * Mengambil semua isi satu kolom sebagai list teks.
	 */
	@Keyword
	def getColumnValues(int col) {
		def cells = DriverFactory.getWebDriver().findElements(By.xpath("//table[@id='table1']/tbody/tr/td[${col}]"))
		return cells.collect { it.getText().trim() }
	}

	/**
	 * Klik header kolom untuk mengurutkan tabel.
	 */
	@Keyword
	def sortByColumn(int col) {
		TestObject header = new TestObject('header_' + col)
		header.addProperty('xpath', ConditionType.EQUALS, "//table[@id='table1']/thead/tr/th[${col}]")
		WebUI.waitForElementClickable(header, 10)
		WebUI.click(header)
	}

	/**
	 * Memverifikasi isi kolom sudah terurut.
	 * ascending = true (naik) / false (turun); numeric = true kalau isinya angka/uang.
	 */
	@Keyword
	def verifyColumnSorted(int col, boolean ascending, boolean numeric) {
		List actual = getColumnValues(col)

		List expectedSorted
		if (numeric) {
			expectedSorted = actual.sort(false) { it.replaceAll('[^0-9.]', '').toBigDecimal() }
		} else {
			expectedSorted = actual.sort(false)
		}
		if (!ascending) {
			expectedSorted = expectedSorted.reverse()
		}

		String arah = ascending ? 'naik' : 'turun'
		println("Kolom ${col} (${arah}) aktual   : " + actual.join(' | '))
		println("Kolom ${col} (${arah}) ekspektasi: " + expectedSorted.join(' | '))
		WebUI.verifyEqual(actual.join(' | '), expectedSorted.join(' | '))
	}
}