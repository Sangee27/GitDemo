package RA;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import POJO.add_place;
import POJO.location;

public class SpecBuilderTest {

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
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
        
		ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification res = given().spec(req).body(a);
		
		Response response = res.when().post("/maps/api/place/add/json")
		.then().spec(resspec).extract().response();
		
		String ResponseString = response.asString();
		System.out.println(ResponseString);

	}

}
