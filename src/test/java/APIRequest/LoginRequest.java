package APIRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.fasterxml.jackson.databind.JsonNode;

import APIResponse.LoginResponse;
import CommonUtils.TestDataLoader;
import Context.EventUserSession;
import Context.ScenarioContext;
import Context.SpecificationClass;
import Context.UserSession;
import EnumClass.APIResources;
import Pojo.LoginRequestpojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LoginRequest {
	
	static Response response;
	
	public static void multipleLoginDetails() throws Exception {

	    JsonNode users = TestDataLoader.loadTestDatafor_Get("REGISTER");

	    List<String> tokens = new ArrayList<>();
	    Map<String, String> tokenMap = new HashMap<>();

	    for (int i = 0; i < users.size(); i++) {

	        JsonNode user = users.get(i);

	        LoginRequestpojo loginPayload = new LoginRequestpojo();
	        loginPayload.setEmail(user.get("email").asText());
	        loginPayload.setPassword(user.get("password").asText());

	        // IMPORTANT: DO NOT pass token for login
	        Response response = LoginRequest.LoginRequest(loginPayload, null);

	        Assert.assertNotNull("Login response is NULL", response);

	        String token1 = response.jsonPath().getString("token");

	        tokens.add(token1);
	        tokenMap.put("user" + i, token1);

	        System.out.println("USER TOKEN: " + token1);
	    }

	    ScenarioContext.getInstance().set("tokens", tokens);
	    ScenarioContext.getInstance().set("tokenMap", tokenMap);
	}
	
	//post logindetails
	
public static Response  LoginRequest(LoginRequestpojo payload,String token) {
		
	
	RequestSpecification reqlog = SpecificationClass.requestHeaderWithToken(token);
	      
	      String endpoint =APIResources.POSTLOGIN.getResource();

	         response = RestAssured.given()
	                .spec(reqlog)
	                .log().all()
	                .body(payload)
	                .when()
	                .post(endpoint)
	                .then()
	                .log().all()
	                .extract()
	                .response();

	        return response;
	}

	//Getting userid
	public static String getuserid(Response response) {
		
		//Assert.assertNotNull("Login response is NULL (check API call)", response);
		
		LoginResponse pojo = response.as(LoginResponse.class);

		String userid = pojo.getUser().getId();
		
		//String email = pojo.getUser().getEmail();
		
		
		//String userId = response.jsonPath().getString("user.id");
		
		//String email = response.jsonPath().getString("user.email");

        System.out.println("id = " + userid);

        Assert.assertNotNull(userid);

        //context.set("userid", userid);

        return userid;
		
	}
	
	//Gettinguseremail

	public static String getuseremail(Response response) {
		
		LoginResponse pojo = response.as(LoginResponse.class);

        String useremail = pojo.getUser().getEmail();

        System.out.println("email = " + useremail);

        Assert.assertNotNull(useremail);

       // context.set("useremail", useremail);

        return useremail;
		
	}
	
	//Get request details
	
	public static Response getUserDetails(int userNumber) {

		
		
		Map<String, String> tokenMap = (Map<String, String>) ScenarioContext.getInstance().get("tokenMap");

	    String token = tokenMap.get("user" + userNumber);
	   
	    RequestSpecification reqget = SpecificationClass.requestHeaderWithToken(token);

	    String endpointget = APIResources.AUTHGETLOGIN.getResource();

	    Response response2 = RestAssured.given()
	            //.spec(reqget)
	            .log().all()
	            .when()
	            .get(endpointget)
	            .then()
	            .log().all()
	            .extract()
	            .response();

	    return response2;
	}

	
	public static Map<String, Response> getMultipleUsersDetails() {

        Map<String, EventUserSession> userMap = (Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");

        Map<String, Response> responseMap = new HashMap<>();

        for (Map.Entry<String, EventUserSession> entry : userMap.entrySet()) {

            String userKey = entry.getKey();
          
            EventUserSession user = entry.getValue();

            String token = user.getToken();

            RequestSpecification req =SpecificationClass.requestHeaderWithToken(token);

            Response response =
                    RestAssured.given()
                            .spec(req)
                            .when()
                            .get(APIResources.AUTHGETLOGIN.getResource())
                            .then()
                            .extract()
                            .response();

            responseMap.put(userKey, response);

            System.out.println(userKey + " -> Status: " + response.getStatusCode());
        }

        return responseMap;
    }

}
