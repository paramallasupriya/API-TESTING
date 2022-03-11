package com.apex.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

/**
 * This class shows how to send a POST Request with JSON using 'HttpPost' method of Apache HttpClient library. 
 */
public class Employee_Post_Request {

	@Test	
	public void createEmployee() throws ClientProtocolException, IOException {

		String postEndpoint = "http://dummy.restapiexample.com/api/v1/create";

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(postEndpoint);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");


		String inputJson = "{\n" +
				"  \"name\": \"priya\",\n" +
				"  \"salary\": \"5000\",\n" +
				"  \"age\": \"20\"\n" +
				"}";

		StringEntity stringEntity = new StringEntity(inputJson);
		httpPost.setEntity(stringEntity);

		System.out.println("Executing request " + httpPost.getRequestLine());

		HttpResponse response = httpclient.execute(httpPost);
		
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));


		//Throw runtime exception if status code isn't 200
		if (response.getStatusLine().getStatusCode() != 200) {
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

