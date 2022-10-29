package RA;

import org.testng.Assert;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.path.json.JsonPath;


public class sumValidation {

	@Test
     public void testSum()
     {
		//JsonPath - Json path is a class which will take string as input and convert it to json
				//COnverted json will be parsed and stored in object
				//Retrieve one value from json list => Using get method
						
				JsonPath js = new JsonPath(Payload.CoursePrice());
				
			
				int sum = 0;

				//Get count of courses
				int count = js.get("courses.size()");
				System.out.println(count);
				
				//Get prices and copies of all courses
				for(int i=0;i<count;i++)
				{
					int price = js.get("courses["+i+"].price");
					int copies = js.get("courses["+i+"].copies");
					int coursePrices = price*copies;
					System.out.println(coursePrices);
					sum = sum + coursePrices;
				}
			   System.out.println(sum);
			   //Get Purchase amount
			   int PurchaseAmount = js.get("dashboard.purchaseAmount");
			   Assert.assertEquals(sum,PurchaseAmount);
			   
			   
			   
				
			}
		
}
