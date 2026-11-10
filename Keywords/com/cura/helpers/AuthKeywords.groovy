package com.cura.helpers

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

class AuthKeywords {

	@Keyword
	def loginToCura(String username, String password) {
		// Klik tombol Make Appointment
		WebUI.click(findTestObject('Page_Home/a_btn-make-appointment'))

		// Isi Username dan Password secara dinamis
		WebUI.setText(findTestObject('Page_Login/input_Username'), username)
		WebUI.setText(findTestObject('Page_Login/input_Password'), password)

		// Klik Login
		WebUI.click(findTestObject('Page_Login/button_btn-login'))
	}
}