package RA;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.nio.file.Paths;

import org.testng.annotations.Test;
import org.testng.reporters.Files;

import Files.Payload;

@Test
public class static_jsonpayloads {

	


//Convert file content to string => Convert to file to bytes -> Byte data to string
public void AddPlace()
{
	RestAssured.baseURI = "https://rahulshettyacademy.com";
	String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
	
	//AddPlace
	//Convert file content to string => Convert to file to bytes -> Byte data to string

	.body(new String(Files.readAllBytes(Paths.get("Users/sangeethap/Downloads"))))
	.when().post("/maps/api/place/add/json")
	.then().assertThat().statusCode(200)
	.extract().response().asString();
	//.header("Server", "Apache/2.4.18 (Ubuntu)");
	
	System.out.println(response);
	
	//JsonPath - Json path is a class which will take string as input and convert it to json
	//COnverted json will be parsed and stored in object
	
	JsonPath js = new JsonPath(response); //Jsonpath had converted string to json and JS object retrierving string from the json
	String place_id = js.getString("place_id");
	
	System.out.println(place_id);
	
}
}
