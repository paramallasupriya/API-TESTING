package com.apex.xml.jaxb.model;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Fruit {
    int id;
    String name;
    String price;

 // getter, setter methods
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

    //toString Method
	
	@Override
	public String toString() {
		//return "Fruit [id=" + id + ", name=" + name + ", price=" + price + "]";
		  return "Fruit{" +
          "id=" + id +
          ", name='" + name + '\'' +
          ", price='" + price + '\'' +
          '}';
	}  
}