package com.apex.samples;

//HttpClient_MultiPart_File_Upload

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public class HttpClient_MultiPart_File_Upload {

    @Test
	public void uploadFile() throws IOException {

        String postEndpoint = "http://httpbin.org/post";

        File testUploadFile = new File("C:\\temp\\testfile.png"); //Specify your own location and file 

        CloseableHttpClient httpclient = HttpClients.createDefault();

        // build httpentity object and assign the file that need to be uploaded 
        HttpEntity postData = MultipartEntityBuilder.create().addBinaryBody("upfile", testUploadFile).build();

        // build http request and assign httpentity object to it that we build above 
        HttpUriRequest postRequest = RequestBuilder.post(postEndpoint).setEntity(postData).build();

        System.out.println("Executing request " + postRequest.getRequestLine());

        HttpResponse response = httpclient.execute(postRequest);

        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

        //Throw runtime exception if status code isn't 200 
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        }

        //Create the StringBuffer object and store the response into it. 
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        System.out.println("Response : \n" + result);
    }
}