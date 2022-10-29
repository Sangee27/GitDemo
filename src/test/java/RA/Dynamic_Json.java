package RA;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;


public class Dynamic_Json {
	
	@Test(dataProvider = "BookInfo")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
	    Response res = given().log().all().header("Content-Type","application/json").body(Payload.AddBook(isbn,aisle)).
	    		when().log().all().post("/Library/Addbook.php").
	    		then().log().all().assertThat().statusCode(200)
	    		.extract().response();
	    System.out.println(res);
	    
	    
	   
		
	}
	

	
	//arrays = Collection of elements 
	//Multidimensional array = Collection of arrays => Object is multidimensional array
	//DataProvider annotation helps us to do parameterization
	@DataProvider(name = "BookInfo")
	public Object[][] getData()
	{
		return new Object[][] {{"sfkdb","9292"},{"dsfdf","2929"},{"sjdfd","3992"}};
		
	}
}


