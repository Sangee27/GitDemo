package RA;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import POJO.Api;
import POJO.GetCourses;
import POJO.webAutomation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class oAuthTest 
{
	public static void main(String[] args) throws InterruptedException 
	{
		String[] courses = {"Protractor","Soap UI","Selenium"};
		System.setProperty("webdriver.chrome.driver","/Users/sangeethap/Downloads/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("sangeetest20@gmail.com");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Sangee@123");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		

		

		String accesstoken_response =given().queryParams("code","")
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","[{\"key\":\"client_secret\",\"value\":\"erZOWM9g3UtwNRj340YYaK_W\",\"equals\":true,\"description\":\"\",\"enabled\":true}]")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.when().post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js = new JsonPath(accesstoken_response);
		String access_token = js.get("access_token");
		System.out.println(access_token);
	
		System.out.println("111");
		System.out.println("222");

		String response = given().queryParams("access_token",access_token)
		.when().get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);
		
		GetCourses gc = given().queryParam(access_token, accesstoken_response).expect().defaultParser(Parser.JSON).
		when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourses.class);
		
		//Deserilization
		
		System.out.println(gc.getInstructor());  
		System.out.println(gc.getLinkedIn());
		
		List<Api> apiCourses = gc.getCourses().getApi();

		for(int i=0;i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices Testing"))
			{
				apiCourses.get(i).getPrice();
			}
			
			
		}
		
		ArrayList<String> a = new ArrayList<String>();
		List<webAutomation> webAutomation = gc.getCourses().getWebAutomation();
		for(int j=0;j<webAutomation.size();j++)
		{
		 a.add(webAutomation.get(j).getCourseTitle());
			
		}
		
		List<String> expected = Arrays.asList(courses);
		
		Assert.assertTrue(a.equals(expected));
		
		

		

	}
		
}


