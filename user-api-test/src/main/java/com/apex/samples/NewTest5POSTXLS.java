package com.apex.samples;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class NewTest5POSTXLS implements UserConstants{

@DataProvider(name = "dp_PostData")
 public Object[][] getSuccessfulData() {
 String[][] data = getTableArray("Test_Data_API.xls", "POST2", "POST2Ids"); 
 return data;
 }
 
@Test(dataProvider = "dp_PostData")
public void testWithSuccessData(String name,String email, String gender, String status) throws ClientProtocolException, IOException {

/*  String payload = "{\n"
      + " \"id\": {{id}}, \n"
      + "  \"name\": {{name}}, \n"
      + "   \"email\": {{email}}, \n"
      + "    \"gender\" : {{gender}},  \n"
      + "     \"status\" : {{status}} \n"
      + "}"; */

  CloseableHttpResponse response = postRequest(BASE_URL_POST,name,email, gender, status);
  
  String result = postResponseString(response);
  
  //Bearer  ="bfe61952daa5549c934d07fe5d9579aec28d819900c949a164aec82da500312a";
  
//Supriya
  Assert.assertEquals(response.getStatusLine().getStatusCode(),201);//(Expected, Actual)
  Assert.assertEquals(response.getStatusLine().getReasonPhrase(),"OK");
 
System.out.println(result);

//Supriya
System.out.println("response : : : " + response);
//Supriya
}

/*
@Test(dataProvider = "dp_PostData")
public void testWithBlank() throws ClientProtocolException, IOException {
 
 
  String payload = "{\"name\": \"\",\"job\": \"\"}";
  CloseableHttpResponse response = postRequest(postEndpoint,payload);
  String result = postResponseString(response);
 
  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
 
System.out.println(result);


}
@Test(dataProvider = "dp_PostData")
public void testWithWrongDatatype() throws ClientProtocolException, IOException {
 

  String payload = "{\"name\": \"123\",\"job\": \"1234\"}";
  CloseableHttpResponse response = postRequest(BASE_URL_POST,payload);
  String result = postResponseString(response);
 
  Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
  Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());
 
System.out.println(result);


}
@Test(dataProvider = "dp_PostData")
public void testWithWrongTagName() throws ClientProtocolException, IOException {
 

  String payload = "{\"email\": \"abc@gmail.com\",\"password\": \"abcd1234\"}";
  CloseableHttpResponse response = postRequest(BASE_URL_POST,payload);
  String result = postResponseString(response);
 
Assert.assertEquals(STATUS_CODE_200,response.getStatusLine().getStatusCode());//(Expected, Actual)
Assert.assertEquals(STATUS_MESSAGE_OK,response.getStatusLine().getReasonPhrase());

System.out.println(result);

}
*/

public String postResponseString(CloseableHttpResponse response) throws IOException {
HttpEntity entity = response.getEntity();
String result = null;
if (entity != null) {
result = EntityUtils.toString(response.getEntity());
}
return result;
}

public CloseableHttpResponse postRequest(String url,String name,String email,String gender,String status) throws UnsupportedEncodingException, IOException, ClientProtocolException {

CloseableHttpClient client = HttpClientBuilder.create().build();

//String payload = "{\"name\": \"morpheus\",\"job\": \"leader\"}";
String payload = "{\n"
   + "  \"name\": \"" + name + "\", \n"
   + "   \"email\": \"" + email + "\", \n"
   + "    \"gender\" : \"" + gender  + "\",  \n"
   + "     \"status\" : \"" + status + "\" \n"
   + "}";
StringEntity input = new StringEntity(payload);

HttpPost request = new HttpPost(url);
//Supriya
request.addHeader("Authorization", "Bearer bfe61952daa5549c934d07fe5d9579aec28d819900c949a164aec82da500312a");
request.setHeader("Accept", "application/json");
request.setHeader("Content-type", "application/json"); 
request.setEntity(input);
//request.setEntity(new StringEntity(payload));

CloseableHttpResponse response = client.execute(request);
return response;
}

public String[][] getTableArray(String xlFilePath, String sheetName, String tableName){
     String[][] tabArray=null;
     try{
         Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
         Sheet sheet = workbook.getSheet(sheetName);
         int startRow,startCol, endRow, endCol,ci,cj;
         Cell tableStart=sheet.findCell(tableName);
         startRow=tableStart.getRow();
         startCol=tableStart.getColumn();
       
         Cell tableEnd= sheet.findCell(tableName, startCol+1,startRow+1, 100, 64000,  false);                              

         endRow=tableEnd.getRow();
         endCol=tableEnd.getColumn();
         System.out.println("startRow="+startRow+", endRow="+endRow+", " +
                 "startCol="+startCol+", endCol="+endCol);
         tabArray=new String[endRow-startRow-1][endCol-startCol-1];
         ci=0;

         for (int i=startRow+1;i<endRow;i++,ci++){
             cj=0;
             for (int j=startCol+1;j<endCol;j++,cj++){
                 tabArray[ci][cj]=sheet.getCell(j,i).getContents();
             }
         }
     }
     catch (Exception e)    {
         System.out.println("error in getTableArray()");
     }

     return(tabArray);
 }
 
}
