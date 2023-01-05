package stepDefinations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils {
	RequestSpecBuilder reqBuilder; 
	ResponseSpecification resSpec;
	RequestSpecification req;
	TestDataBuild data = new TestDataBuild();
	Response response;
	static String place_id;
	
	
		@Given("Add Place Payload with {string} {string} {string}")
		public void add_place_payload_with(String name, String language, String address) throws IOException {
		    // Write code here that turns the phrase above into concrete actions
			req = given().spec(requestSpecification()).body(data.addPlacePayload(name,language,address));
		}


		@When("user calls {string} with {string} http request")
		public void user_calls_with_http_request(String resource, String method) {
		    //Constructor will be called with value of resource which you pass 
			APIResources apiResouce = APIResources.valueOf(resource);
			if(method.equalsIgnoreCase("POST")) 
				response = req.when().post(apiResouce.getResource());
			else if(method.equalsIgnoreCase("GET"))
				response = req.when().get(apiResouce.getResource());
			else if(method.equalsIgnoreCase("DELETE"))
				response = req.when().delete(apiResouce.getResource());
			
		}
		
		@Then("the API call got success with status code {int}")
		public void statusCode(int expectedStatusCode) {
		  int statuscode = response.getStatusCode();
		   assertEquals(expectedStatusCode, statuscode);
		}
		
		@Then("{string} in response body is {string}")
		public void in_response_body_is(String key, String expectedvalue) {				
		   assertEquals(getJsonPath(response, key), expectedvalue);
		}
		
		

		@Then("verify place_Id created maps to {string} using {string}")
		public void verify_place_id_created_maps_to_using(String expectedname, String resoucename) throws IOException {
		   
		    place_id = getJsonPath(response, "place_id");
		    req = given().spec(requestSpecification()).queryParam("place_id", place_id);			
			user_calls_with_http_request(resoucename,"GET"); 
			String actualname =	getJsonPath(response, "name"); 
			assertEquals(actualname, expectedname);
			 
		}
		
		
		@Given("DeletePlace Payload")
		public void delete_place_payload() throws IOException {
			
			req = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
			
		    
		}



}
