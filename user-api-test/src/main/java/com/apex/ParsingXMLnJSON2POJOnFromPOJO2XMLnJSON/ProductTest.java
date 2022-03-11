package com.apex.ParsingXMLnJSON2POJOnFromPOJO2XMLnJSON;

import org.apache.juneau.html.HtmlSerializer;
import org.apache.juneau.json.JsonParser;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.parser.ParseException;
import org.apache.juneau.serializer.SerializeException;
import org.apache.juneau.xml.XmlSerializer;
//import org.apache.juneau.parser.ReaderParser;
//import org.apache.juneau.xml.XmlParser;


public class ProductTest {

	public static void main(String[] args) throws SerializeException, ParseException {
		// TODO Auto-generated method stub

		// Serialization
		// POJO to JSON
		JsonSerializer jsonSerializer = JsonSerializer.DEFAULT_READABLE;

		String sellerNames[] = { "ABC enterprises", "XYZ Softwares", "LMN services" };
		Product product = new Product("MACbookPro", 1000, "Gold", sellerNames);
		String jsonVal = jsonSerializer.serialize(product);// Need to add throws for SerializeException
		System.out.println("JSON Format jsonVal" + jsonVal);

		// POJO to XML

		XmlSerializer xmlSerializer = XmlSerializer.DEFAULT_NS_SQ_READABLE;
		String xml = xmlSerializer.serialize(product);
		System.out.println("XML Format xml" + xml);

		// POJO to HTML

		HtmlSerializer htmlSerializer = HtmlSerializer.DEFAULT_SQ_READABLE;
		String html = htmlSerializer.serialize(product);
		System.out.println("HTML Format html" + html);

		// Deserialization

		// JSON TO POJO

		JsonParser jsonParser = JsonParser.DEFAULT;
		String Json = "{\n" + "\"color\":\"Gold\",\n" + "\"name\":\"MACbookPro\",\n" + "\"price\":1000,\n"
				+ "\"sellerNames\":[\"ABC enterprises\",\"XYZ Softwares\",\"LMN services\"]\n" + "}";
		Product pro = jsonParser.parse(Json, Product.class);// Need to add throws for :ParseException
		System.out.println(pro.getColor());
		System.out.println(pro);
	}

	 
}
