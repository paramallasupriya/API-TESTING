package com.apex.jsonXML;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JSON2JsonElement {
	public static void main(String[] args) {
		// pretty print
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (Reader reader = new FileReader("C:\\SupriyaP\\JsonXMLProjects\\staff.json")) {
			// Convert JSON to JsonElement, and later to String
			JsonElement json = gson.fromJson(reader, JsonElement.class);
			String jsonInString = gson.toJson(json);
			System.out.println(json);
			System.out.println(jsonInString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}