package com.apex.samples;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NewTestNG2 {
	//private String result;
	String result = "";

	@Test
	public void testGetWithSuccessData() throws ClientProtocolException, IOException {

		String URL = "https://gorest.co.in/public-api/users/1383";

		HttpResponse response = sendGetRequest(URL);

		// 4. Validate response
		// int StatusCode = response.getStatusLine().getStatusCode();
		// String StatusMessage = response.getStatusLine().getReasonPhrase();
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);// (Actual,Expected)
		Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");// (Actual,Expected)
		
		result = getResponseString(response);
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

	private HttpResponse sendGetRequest(String url) throws IOException, ClientProtocolException {
		// 1. Create Client
		HttpClient client = HttpClientBuilder.create().build();

		// 2. Request Message
		// String URL = "https://gorest.co.in/public-api/users/1383";
		HttpGet RequestMsg = new HttpGet(url);

		// 3.Send and receive response
		HttpResponse response = client.execute(RequestMsg);
		return response;
	}

	@Test
	public void testGetWithBlank() throws ClientProtocolException, IOException {
		String URL = "https://gorest.co.in/public-api/users/";

		HttpResponse response = sendGetRequest(URL);

		// 4. Validate response
		// int StatusCode = response.getStatusLine().getStatusCode();
		// String StatusMessage = response.getStatusLine().getReasonPhrase();
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);// (Actual,Expected)
		Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");// (Actual,Expected)
		
		result = getResponseString(response);

	}

	@Test
	public void testGetWithZero() throws ClientProtocolException, IOException {
		String URL = "https://gorest.co.in/public-api/users/0";

		HttpResponse response = sendGetRequest(URL);

		// 4. Validate response
		// int StatusCode = response.getStatusLine().getStatusCode();
		// String StatusMessage = response.getStatusLine().getReasonPhrase();
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);// (Actual,Expected)
		Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");// (Actual,Expected)
		
		result = getResponseString(response);

	}

	@Test
	public void testGetWithNumber() throws ClientProtocolException, IOException {
		String URL = "https://gorest.co.in/public-api/users/789988";

		HttpResponse response = sendGetRequest(URL);

		// 4. Validate response
		// int StatusCode = response.getStatusLine().getStatusCode();
		// String StatusMessage = response.getStatusLine().getReasonPhrase();
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);// (Actual,Expected)
		Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");// (Actual,Expected)
		
		result = getResponseString(response);

	}

	@Test
	public void testGetWithWrongDataType() throws ClientProtocolException, IOException {
		String URL = "https://gorest.co.in/public-api/users/abcd";

		HttpResponse response = sendGetRequest(URL);

		// 4. Validate response
		// int StatusCode = response.getStatusLine().getStatusCode();
		// String StatusMessage = response.getStatusLine().getReasonPhrase();
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);// (Actual,Expected)
		Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");// (Actual,Expected)
		
		result = getResponseString(response);

	}

	@Test
	public void testGetWithWrongPassword() throws ClientProtocolException, IOException {
		String URL = "https://gorest.co.in/public-api/users/PPPPPPP";

		HttpResponse response = sendGetRequest(URL);

		// 4. Validate response
		// int StatusCode = response.getStatusLine().getStatusCode();
		// String StatusMessage = response.getStatusLine().getReasonPhrase();
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);// (Actual,Expected)
		Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");// (Actual,Expected)
		
		result = getResponseString(response);

	}

	@Test
	public void testGetWithWrongUserName() throws ClientProtocolException, IOException {
		String URL = "https://gorest.co.in/public-api/users/LLLLLL";

		HttpResponse response = sendGetRequest(URL);

		// 4. Validate response
		// int StatusCode = response.getStatusLine().getStatusCode();
		// String StatusMessage = response.getStatusLine().getReasonPhrase();
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);// (Actual,Expected)
		Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");// (Actual,Expected)
		
		result = getResponseString(response);

	}
}
