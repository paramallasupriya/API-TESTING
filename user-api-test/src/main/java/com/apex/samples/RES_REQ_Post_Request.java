package com.apex.samples;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * This class shows how to send a POST Request with XLS Data using 'HttpPost' method of Apache HttpClient library. 
 */
public class RES_REQ_Post_Request implements UserConstants {

@DataProvider(name = "dp_PostData")
public Object[][] getData() {

	String[][] data = getTableArray("Test_Data_API.xls", "POST2", "POST3Ids"); 

	return data;
}

//Data driven Testing using XLS

public String[][] getTableArray(String xlFilePath, String sheetName, String tableName) {
	String[][] tabArray = null;
	try {
		Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));// Test_Data_API.xls
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

@Test(dataProvider = "dp_PostData")
	public void createEmployee(String email,String first_name,String last_name) throws ClientProtocolException, IOException {

		String postEndpoint = "https://reqres.in/api/users";

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(postEndpoint);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		//httpPost.addHeader("Authorization", "Bearer bfe61952daa5549c934d07fe5d9579aec28d819900c949a164aec82da500312a");
		
		String inputJson = "{\n"
				   + "   \"email\": \"" + email + "\", \n"
				   + "    \"first_name\" : \"" + first_name  + "\",  \n"
				   + "     \"last_name\" : \"" + last_name + "\" \n"
				   + "}";
		
		StringEntity stringEntity = new StringEntity(inputJson);
		httpPost.setEntity(stringEntity);

		System.out.println("Executing request " + httpPost.getRequestLine());

		HttpResponse response = httpclient.execute(httpPost);
		
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));


		//Throw runtime exception if status code isn't 201
		if (response.getStatusLine().getStatusCode() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
		}


		//Create the StringBuffer object and store the response into it.
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null) {
			System.out.println("Response : \n"+result.append(line));
		}

	}
}

