package RA;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import POJO.add_place;
import POJO.location;

public class serializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		add_place a = new add_place();
		a.setAccuracy(30);
		a.setAddress("Africa walkway");
		a.setLanguage("English");
		a.setName("Cohen");
		a.setPhone_number("8272828289");
		//Json - ArrayList => Need to create arraylist on serialization
		List<String> myList = new ArrayList<String>();
		myList.add("Shoe Park");
		myList.add("Shop");
		a.setTypes(myList);
		//Importing sub class - Create object of subclass and access
		location l = new location();
		l.setLat(38.383494);
		l.setLng(33.427362);
		a.setLocation(l);
		
		

		String response = given().queryParam("key", "qaclick123")
		.body(a)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);
	}

}
