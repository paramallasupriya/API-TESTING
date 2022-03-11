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

public class NewTestNG3 {
	String result = "";
  @Test
  public void TestCase1()throws ClientProtocolException, IOException{
	    
	   String URL = "https://gorest.co.in/public-api/users/1383";
		
		HttpResponse response = sendGetRequest(URL);

		// 4. Validate response
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		Assert.assertEquals(response.getStatusLine().getReasonPhrase(), "OK");

		result = getResponseString(response);
 }


public String getResponseString(HttpResponse response) throws IOException {
	HttpEntity entity = response.getEntity();
	if (entity != null) {
		// return it as a String
		String result = EntityUtils.toString(entity);
		System.out.println(result);
	}
return result;	
}


public HttpResponse sendGetRequest(String url) throws IOException, ClientProtocolException {
	// 1. Create Client
	HttpClient client = HttpClientBuilder.create().build();

	// 2. Request Message
	HttpGet RequestMsg = new HttpGet(url);

	// 3.Send and receieve response
	HttpResponse response = client.execute(RequestMsg);
	return response;
}

}

