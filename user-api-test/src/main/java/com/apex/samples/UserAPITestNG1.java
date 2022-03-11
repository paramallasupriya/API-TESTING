package com.apex.samples;

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
//import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserAPITestNG1 implements UserConstants {

String result = "";

private HttpResponse sendGetRequest(String URL) throws IOException, ClientProtocolException {
	//Step 1: Create a client
		 HttpClient client = HttpClientBuilder.create().build();
		 //Step2. Request message
		 HttpGet requestMsg= new HttpGet(URL);
		 //Step3: Send and receive the response message
		 HttpResponse response = client.execute(requestMsg);
	return response;
}

private String getResponseString(HttpResponse response) throws IOException {
	HttpEntity entity = response.getEntity();	
     if (entity != null) 
     {
	  // return it as a String
    	 result = EntityUtils.toString(entity);
    	 System.out.println(result);
	 }
	return result;	
}
@Test
public void testGetwithSuccessData()  throws ParseException, IOException
{
	  String URL = BASE_URL+ "/" + 137;
	  HttpResponse response = sendGetRequest(URL);
      Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  result = getResponseString(response);
	  System.out.println("Test Result for testGetwithSuccessData " + result);
	  	  
}

  @Test
  public void testGetwithBlank() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/ ";// When blank is provided in the end it throws IllegalArgumentException
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  result = getResponseString(response);
	  System.out.println("Test Result for testGetwithBlank " + result);//java.lang.IllegalArgumentException: Illegal character in path at index 38: https://gorest.co.in/public-api/users/ 
	  //Assert.assertTrue(result.contains("\"code\":200"));//"code":200 : in case no blank is provided in the end of the URL
  }
  
  @Test
  public void testGetwithWrongDataType() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/ABC";
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  result = getResponseString(response);
	  System.out.println("Test Result for testGetwithWrongDataType " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""

	  
	  
  }
  

  @Test
  public void testGetwithZero() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/0";
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithZero " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""

  }
  
  
  @Test
  public void testGetwithNumber() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/679890";//Invalid Number
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithNumber " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""
  }
  
  
  @Test
  public void testGetwithCharacters() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/***";
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithCharacters " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""
  }
  
 
  @Test
  public void testGetwithAlphaNumerics() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/ABC12345";
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithAlphaNumerics " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""
  }
  
  @Test
  public void testGetwithSpecialCharacters() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/@#$%^^&";//java.lang.IllegalArgumentException
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithSpecialCharacters " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""
  }
  
  
  @Test
  public void testGetwithBlankInFirstPosition() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/ 1383";// When blank is provided before the number: it throws IllegalArgumentException
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithBlankInFirstPosition " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""
  }
  
  @Test
  public void testGetwithBlankInLastPosition() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/1383 ";// When blank is provided after the number: it throws IllegalArgumentException
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithBlankInLastPosition " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""
  }
  
  
  @Test
  public void testGetwithNegativeNumber() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/-1383";//Invalid Number
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithNegativeNumber " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""
  }
  
  @Test
  public void testGetwithUpperCaseLetters() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/ABCDEF";
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithUpperCaseLetters " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""
  }
  
  
  
  @Test
  public void testGetwithLowerCaseLetters() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/abcdef";
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithLowerCaseLetters " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""
  }
 
  @Test
  public void testGetwithMixOfUpperNLowerCaseLetters() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/ABCdef";
	  HttpResponse response = sendGetRequest(URL);
	  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithMixOfUpperNLowerCaseLetters " + result);
	  Assert.assertTrue(result.contains("\"code\":404"));//"code":404
	  Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""
  }
  
  
  @Test
  public void testGetwithHTMLCharacters() throws ClientProtocolException, IOException 
  {
	  String URL = BASE_URL+"/<!$#>";//java.lang.IllegalArgumentException
	  HttpResponse response = sendGetRequest(URL);
	  //Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
	  //Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
	  String result = getResponseString(response);
	  System.out.println("Test Result for testGetwithHTMLCharacters " + result);
	  //Assert.assertTrue(result.contains("\"code\":400"));//"code":404
	  //Assert.assertTrue(result.contains("\"message\":\"Resource not found\""));//\"message\":\"Resource not found\""
  }
  
  
}
