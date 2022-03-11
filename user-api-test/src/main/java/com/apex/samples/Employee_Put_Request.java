package com.apex.samples;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;
/**
 * This class shows how to send a PUT Request with JSON using 'HttpPut' method of Apache HttpClient library. 
 **/
public class Employee_Put_Request {
    @Test
    public void updateEmployee() throws ClientProtocolException, IOException {

        String putEndpoint = "http://dummy.restapiexample.com/api/v1/update/2747";//2747

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPut httpPut = new HttpPut(putEndpoint);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        String inputJson = "{\n" +
            "  \"name\": \"put_test_employee12345\",\n" +
            "  \"salary\": \"9876543\",\n" +
            "  \"age\": \"23\"\n" +
            "}";

        StringEntity stringEntity = new StringEntity(inputJson);
        httpPut.setEntity(stringEntity);
       		
        System.out.println("Executing request " + httpPut.getRequestLine());

        HttpResponse response = httpclient.execute(httpPut);
 
        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

        //Throw runtime exception if status code isn't 200 
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        }
        //Create the StringBuffer object and store the response into it. 
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
		   System.out.println("Response : \n" + result.append(line));
        }
    }
}