package com.apex.samples;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class UserAPI2 {
	public static void main(String[] Args) throws ClientProtocolException, IOException {
		// 1. Create Client
		HttpClient client = HttpClientBuilder.create().build();

		// 2. Request Message
		String URL = "https://gorest.co.in/public-api/users/1383";
		HttpGet RequestMsg = new HttpGet(URL);

		// 3.Send and receieve response
		HttpResponse response = client.execute(RequestMsg);

		// 4. Validate response
		int StatusCode = response.getStatusLine().getStatusCode();
		String StatusReason = response.getStatusLine().getReasonPhrase();

		System.out.println(StatusReason);

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// return it as a String
			String result = EntityUtils.toString(entity);
			System.out.println(result);
		}
			if (StatusCode == 200)
				System.out.println("Test is Successful");
			else {
				System.out.println("Test is failure");
			}

			// 5. Close
			
	}

}
