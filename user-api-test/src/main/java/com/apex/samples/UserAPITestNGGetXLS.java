package com.apex.samples;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class UserAPITestNGGetXLS implements UserConstants {

	String result = "";

	private HttpResponse sendGetRequest(String URL) throws IOException, ClientProtocolException {
		// Step 1: Create a client
		HttpClient client = HttpClientBuilder.create().build();
		// Step2. Request message
		HttpGet requestMsg = new HttpGet(URL);
		// Step3: Send and receive the response message
		HttpResponse response = client.execute(requestMsg);
		return response;
	}

	private String getResponseString(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// return it as a String
			result = EntityUtils.toString(entity);
			System.out.println(result);
		}
		return result;
	}

	@DataProvider(name = "dp_successdata")
	public Object[][] getData() {
		String[][] data = getTableArray("Test_Data_API.xls", "User_API_TestIds", "SuccessIds");

		return data;
	}

//Data driven Testing using XLS

	public String[][] getTableArray(String xlFilePath, String sheetName, String tableName) {
		String[][] tabArray = null;
		try {
			Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));// Test Data_API.xls
			Sheet sheet = workbook.getSheet(sheetName);// User API TestIds
			int startRow, startCol, endRow, endCol, ci, cj;
			Cell tableStart = sheet.findCell(tableName);
			startRow = tableStart.getRow();
			startCol = tableStart.getColumn();

			Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1, 100, 64000, false);

			endRow = tableEnd.getRow();
			endCol = tableEnd.getColumn();
			System.out.println("startRow=" + startRow + ", endRow=" + endRow + ", " + "startCol=" + startCol
					+ ", endCol=" + endCol);
			tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
			ci = 0;

			for (int i = startRow + 1; i < endRow; i++, ci++) {
				cj = 0;
				for (int j = startCol + 1; j < endCol; j++, cj++) {
					tabArray[ci][cj] = sheet.getCell(j, i).getContents();
				}
			}
		} catch (Exception e) {
			System.out.println("error in getTableArray()");
			e.printStackTrace();
		}

		return (tabArray);
	}

	@Test(dataProvider = "dp_successdata")
	public void testGetwithSuccessData(String id, String name) throws ParseException, IOException {
		String URL = BASE_URL + "/" + id;
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		result = getResponseString(response);
		System.out.println("Test Result for testGetwithSuccessData " + result);

	}

	@Test
	public void testGetwithBlank() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/ ";// When blank is provided in the end it throws IllegalArgumentException
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		result = getResponseString(response);
		System.out.println("Test Result for testGetwithBlank " + result);// java.lang.IllegalArgumentException: Illegal
																			// character in path at index 38:
																			// https://gorest.co.in/public-api/users/
		// Assert.assertTrue(result.contains("\"code\":200"));//"code":200 : in case no
		// blank is provided in the end of the URL
	}

	@Test
	public void testGetwithWrongDataType() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/ABC";
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		result = getResponseString(response);
		System.out.println("Test Result for testGetwithWrongDataType " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""
	}

	@Test
	public void testGetwithZero() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/0";
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithZero " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""

	}

	@Test
	public void testGetwithNumber() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/679890";// Invalid Number
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithNumber " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""
	}

	@Test
	public void testGetwithCharacters() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/***";
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithCharacters " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""
	}

	@Test
	public void testGetwithAlphaNumerics() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/ABC12345";
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithAlphaNumerics " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""
	}

	@Test
	public void testGetwithSpecialCharacters() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/@#$%^^&";// java.lang.IllegalArgumentException
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithSpecialCharacters " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""
	}

	@Test
	public void testGetwithBlankInFirstPosition() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/ 1383";// When blank is provided before the number: it throws IllegalArgumentException
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithBlankInFirstPosition " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""
	}

	@Test
	public void testGetwithBlankInLastPosition() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/1383 ";// When blank is provided after the number: it throws IllegalArgumentException
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithBlankInLastPosition " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""
	}

	@Test
	public void testGetwithNegativeNumber() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/-1383";// Invalid Number
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithNegativeNumber " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""
	}

	@Test
	public void testGetwithUpperCaseLetters() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/ABCDEF";
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithUpperCaseLetters " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""
	}

	@Test
	public void testGetwithLowerCaseLetters() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/abcdef";
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithLowerCaseLetters " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""
	}

	@Test
	public void testGetwithMixOfUpperNLowerCaseLetters() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/ABCdef";
		HttpResponse response = sendGetRequest(URL);
		Assert.assertEquals(STATUS_CODE_200, response.getStatusLine().getStatusCode());// (Expected, Actual)
		Assert.assertEquals(STATUS_MESSAGE_OK, response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithMixOfUpperNLowerCaseLetters " + result);
		Assert.assertTrue(result.contains("\"code\":404"));// "code":404
		Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));// \"message\":\"Resource not found\""
	}

	@Test
	public void testGetwithHTMLCharacters() throws ClientProtocolException, IOException {
		String URL = BASE_URL + "/<!$#>";// java.lang.IllegalArgumentException
		HttpResponse response = sendGetRequest(URL);
		// Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected,
		// Actual)
		// Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
		String result = getResponseString(response);
		System.out.println("Test Result for testGetwithHTMLCharacters " + result);
		// Assert.assertTrue(result.contains("\"code\":400"));//"code":404
		// Assert.assertTrue(result.contains("\"message\":\"Resource not
		// found\""));//\"message\":\"Resource not found\""
	}

}
