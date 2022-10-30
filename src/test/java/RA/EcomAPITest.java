package RA;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import POJO.LoginResponse;
import POJO.Order;
import POJO.loginRequest;
import POJO.orderDetail;

public class EcomAPITest {

	public static void main(String[] args) {   
		// TODO Auto-generated method stub
		
		//Request Spec
	    RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
	    
	    //Create object of pojo class and access json 
	    loginRequest loginreq = new loginRequest();
	    loginreq.setuserEmail("sangeethaapadmanaban@gmail.com");
	    loginreq.setuserPassword("Sangee@123");
//	    Header head = new Header(null, null);
	   
	    //Bypass SSl certification - relaxedHTTPSValidation method is used
	   RequestSpecification loginReq = given().relaxedHTTPSValidation().log().all().spec(req).body(loginreq);
	   
	   LoginResponse loginRes = loginReq
			   .when().log().all()
			   .post("/api/ecom/auth/login").then().log().all().assertThat().statusCode(200)
			   .extract().response()
	   .as(LoginResponse.class);
	   String token = loginRes.getToken();
	   System.out.println(token);
	   String userID = loginRes.getUserId();
	   System.out.println(userID);
	   System.out.print("xnjfngjgj");
	   //System.out.println(loginRes.getUserID());
	   
	   
	   //Create Product
	   
	    RequestSpecification addProductRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
	    
	    RequestSpecification ReqAddProduct = given().spec(addProductRequest).param("productName", "Outfits")
		.param("productAddedBy", userID).param("productCategory", "fashion")
		.param("productSubCategory", "shirts").param("productPrice", "11500")
		.param("productDescription", "Lenova").param("productFor", "men")
		.multiPart("productImage",new File("/Users/Sangeethap/Downloads/img3.jpg"));
	    
	    String addProductResponse = ReqAddProduct.when().post("/api/ecom/product/add-product").then().log().all().assertThat().statusCode(201).extract().response().asString();
	    JsonPath js = new JsonPath(addProductResponse);
	    String product_id = js.get("productId");
	    
	    
	    //Create Order
	    RequestSpecification CreateOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).setContentType(ContentType.JSON).build();
	    
	    //Access Array List
	    orderDetail OrderDetail = new orderDetail();
	    OrderDetail.setCountry("India");
	    OrderDetail.setProductOrderedId(product_id);
	    
	    //Creating Array List for order detail
	    List<orderDetail> myOrderList = new ArrayList<orderDetail>();
	    myOrderList.add(OrderDetail);
	    
	    
	    //Access order file which is json
	    Order order = new Order();
	    order.setOrders(myOrderList);
	    
	    
	   RequestSpecification CreateOrderReq =  given().spec(CreateOrderBaseReq).body(order);
	   
	   String Response = CreateOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
	    
	   System.out.println(Response);
	   
	   
	   //Delete Product 
	   
	   RequestSpecification deleteBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).setContentType(ContentType.JSON).build();
	   
	   
	   RequestSpecification deleteprodReq = given().spec(deleteBaseReq).pathParam("productID", product_id);
	   
	   
	   String deleteResponse = deleteprodReq.when().delete("/api/ecom/product/delete-product/{productID}").then().statusCode(200).extract().response().asString();
	   JsonPath js1 = new JsonPath(deleteResponse);
	   
	   Assert.assertEquals("Product Deleted Successfully",js1.getString("message"));
	    
	    		
	    
	    
	    
	    
	   
	   
	    

	}

}
