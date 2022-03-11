package com.apex.xml.jaxb.model;

import java.io.File;
import java.io.StringWriter;

//import com.apex.xml.jaxb.model.Fruit;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class MarshallingJavaObj2XML {

	public static void main(String[] args) {

		//JAXBContext jaxbContext = null;
		try {
			
			//Creating object of the Fruit class and setting the values for different variables it have.
			Fruit o = new Fruit();
			o.setId(1);
			o.setName("Apple");
			o.setPrice("19.99");

			// Creating JAXB Context object, to create JAXB context object we can use newInstance()[This is a static factory method] from JAXBContext class 
			JAXBContext	jaxbContext = JAXBContext.newInstance(Fruit.class);

			// Creating JAXB Context object
			//jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { Fruit.class },null);

			// Creating Marshaller object
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			
			//Call Marshal method from Marshaller object
			// output to a xml file
			jaxbMarshaller.marshal(o, new File("fruit.xml"));//C:\\SupriyaP\\JsonXMLProjects\\

			// output to console
			//jaxbMarshaller.marshal(o, System.out);
			
			// output to writer and display on console
		StringWriter writer = new StringWriter();
		jaxbMarshaller.marshal(o,writer);
		System.out.println(writer.toString());

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}