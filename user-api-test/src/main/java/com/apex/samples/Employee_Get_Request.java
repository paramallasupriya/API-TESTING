	package com.apex.samples;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;

	import org.apache.http.HttpResponse;
	import org.apache.http.client.ClientProtocolException;
	import org.apache.http.client.methods.HttpGet;
	import org.apache.http.impl.client.CloseableHttpClient;
	import org.apache.http.impl.client.HttpClients;
	import org.testng.annotations.Test;

	/**
	 * This class shows how to send a GET Request using 'HttpGet' method of Apache HttpClient library. 
	 */
	public class Employee_Get_Request {


	        @Test	
		    public void getEmployees() throws ClientProtocolException, IOException {

			String getEndpoint = "http://dummy.restapiexample.com/api/v1/employees";

			CloseableHttpClient httpclient = HttpClients.createDefault();

			HttpGet httpget = new HttpGet(getEndpoint);
			System.out.println("Executing request " + httpget.getRequestLine());

			HttpResponse response = httpclient.execute(httpget);

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