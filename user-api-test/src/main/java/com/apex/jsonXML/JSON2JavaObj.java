package com.apex.jsonXML;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JSON2JavaObj {
	public static void main(String[] args) {
		// Gson gson = new Gson();
		// pretty print
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (Reader reader = new FileReader("C:\\SupriyaP\\JsonXMLProjects\\staff.json")) {
			// Convert JSON File to Java Object
			Staff staff = gson.fromJson(reader, Staff.class);
			String jsonInString = gson.toJson(staff);
			// print staff object
			System.out.println("staff :" + staff);
			System.out.println("jsonInString :" + jsonInString);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}