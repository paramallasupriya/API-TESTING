package com.apex.samples;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook; 

/** 
 * This class shows how to send a DELETE Request using 'HttpDelete' method of Apache HttpClient library. 
 */

public class UserAPITestNGDeleteXLS {

@DataProvider (name="dp_DeleteData")
public Object[][] getData(){
	String[][] data = getTableArray("Test_Data_API.xls","DELETE","DeleteIds");
		return data;
}

//Data driven Testing using XLS

public String[][] getTableArray(String xlFilePath, String sheetName, String tableName){
      String[][] tabArray=null;
      try{
          Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));//Test_Data_API.xls
          Sheet sheet = workbook.getSheet(sheetName);//Delete
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
          e.printStackTrace();
      }
      return(tabArray);
  }

@Test(dataProvider = "dp_DeleteData") 
 
	public void deleteEmployee(String id, String name) throws ClientProtocolException, IOException {

		String deleteEndpoint = "http://dummy.restapiexample.com/api/v1/delete/"+id;

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpDelete httpDelete = new HttpDelete(deleteEndpoint);
		System.out.println("Executing request " + httpDelete.getRequestLine());

		HttpResponse response = httpclient.execute(httpDelete);

		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()))); 

		//Throw runtime exception if status code isn't 200 
		if (response.getStatusLine().getStatusCode() != 200) { 
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode()); 
		} 

		//Create the StringBuffer object and store the response into it. 
		StringBuffer result = new StringBuffer(); 
		String line = ""; 
		while ((line = br.readLine()) != null) { 
			System.out.println("Response : \n"+result.append(line)); 
		} 

	} 

}