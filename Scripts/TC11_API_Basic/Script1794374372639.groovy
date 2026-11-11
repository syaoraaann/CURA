import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper

String baseUrl = 'https://jsonplaceholder.typicode.com'

// Fungsi pembantu: bikin request supaya tidak menulis ulang berkali-kali
def buatRequest(String nama, String url, String method, String body = null) {
	RequestObject req = new RequestObject(nama)
	req.setRestUrl(url)
	req.setRestRequestMethod(method)
	req.setHttpHeaderProperties([
		new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/json; charset=UTF-8')
	])
	if (body != null) {
		req.setBodyContent(new HttpTextBodyContent(body, 'UTF-8', 'application/json'))
	}
	return req
}

// ===== 1. GET satu data =====
def resp1 = WS.sendRequest(buatRequest('getPost', "${baseUrl}/posts/1", 'GET'))
WS.verifyResponseStatusCode(resp1, 200)
WS.verifyElementPropertyValue(resp1, 'id', 1)
WS.verifyElementPropertyValue(resp1, 'userId', 1)
println("1. GET /posts/1 -> status " + resp1.getStatusCode())

// ===== 2. GET daftar data, cek jumlahnya =====
def resp2 = WS.sendRequest(buatRequest('getPosts', "${baseUrl}/posts", 'GET'))
WS.verifyResponseStatusCode(resp2, 200)
def daftar = new JsonSlurper().parseText(resp2.getResponseText())
println("2. Jumlah post: " + daftar.size())
WS.verifyEqual(daftar.size(), 100)

// ===== 3. POST buat data baru =====
String body = '{"title": "belajar api", "body": "isi percobaan", "userId": 7}'
def resp3 = WS.sendRequest(buatRequest('createPost', "${baseUrl}/posts", 'POST', body))
WS.verifyResponseStatusCode(resp3, 201)
WS.verifyElementPropertyValue(resp3, 'title', 'belajar api')
WS.verifyElementPropertyValue(resp3, 'userId', 7)
println("3. POST -> status " + resp3.getStatusCode() + " | " + resp3.getResponseText())

// ===== 4. Uji negatif: data yang tidak ada harus 404 =====
def resp4 = WS.sendRequest(buatRequest('getNotFound', "${baseUrl}/posts/99999", 'GET'))
WS.verifyResponseStatusCode(resp4, 404)
println("4. GET /posts/99999 -> status " + resp4.getStatusCode())

// ===== 5. Cek waktu respon =====
println("5. Waktu respon GET: " + resp1.getElapsedTime() + " ms")
WS.verifyLessThan(resp1.getElapsedTime(), 3000L)