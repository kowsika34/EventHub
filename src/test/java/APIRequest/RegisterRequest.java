package APIRequest;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;

import static org.testng.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import APIResponse.LoginResponse;
import APIResponse.RegisterRequestResponse;
import CommonUtils.ConfigReader;
import CommonUtils.TestDataLoader;
import Context.EventUserSession;
import Context.ScenarioContext;
import Context.SpecificationClass;
import Context.UserSession;
import Pojo.LoginRequestpojo;
import Pojo.RegisterRequestpojo;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.Registerpayload;
import EnumClass.APIResources;

public class RegisterRequest extends SpecificationClass {
	
	 
	Map<String, EventUserSession> userMap = new HashMap<>();
	
	static ScenarioContext context =ScenarioContext.getInstance();

	Registerpayload regpayload = new Registerpayload();
	
	static String token;
	
	private static RequestSpecification RequestSpecification;
	

public RegisterRequest() throws FileNotFoundException {
		
	super();
		
	}

//---------------------
//MutipleUser
//--------------------

    public static EventUserSession registerUser(RegisterRequestpojo payload) {

        Response response = RestAssured.given()
                        .spec(SpecificationClass.requestHeaderWithoutToken())
                        .body(payload)
                        .when()
                        .post(APIResources.REGISTER.getResource())
                        .then()
                        .extract().response();

        EventUserSession session = new EventUserSession();

        session.setToken(response.jsonPath().getString("token"));
        session.setUserId(response.jsonPath().getString("user.id"));
        session.setEmail(payload.getEmail());

        return session;
    }

    //----------------
    //countless mutipleuser
    //-------------------------
    
    public static Response registerAllUsers() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        //Load the data
        JsonNode usersArray = TestDataLoader.loadTestDatafor_Get("REGISTER");

        Map<String, EventUserSession> userMap = new HashMap<>();

        Response lastResponse = null;

        for (int i = 0; i < usersArray.size(); i++) {

            JsonNode userNode = usersArray.get(i);

            RegisterRequestpojo payload = mapper.treeToValue(userNode, RegisterRequestpojo.class);

            //API call
            Response response = sendRegisterRequest(payload);
            
            
            //StatusCode validation
            Assert.assertEquals(201, response.getStatusCode());

            lastResponse = response;

            //Extract the token and values
            String token = response.jsonPath().getString("token");

            String userId = response.jsonPath().getString("user.id");

            String email =  payload.getEmail();

            EventUserSession session = new EventUserSession();

            session.setToken(token);
            session.setUserId(userId);
            session.setEmail(email);

            userMap.put("user" + i, session);

            System.out.println("USER = " + email);
            System.out.println("TOKEN = " + token);
        }

        ScenarioContext.getInstance()
                .set("userMap", userMap);

        return lastResponse;
    }

//--------------
 //Counting users
//-------------------
    public static Map<String, EventUserSession> registerMultipleUsers(int totalUsers) throws Exception {

        Map<String, EventUserSession> userMap = new HashMap<>();

        for (int i = 0; i < totalUsers; i++) {

        	
        	//1.Load Test data
            RegisterRequestpojo payload = TestDataLoader.loadTestDatafor_Post_Put("REGISTER", RegisterRequestpojo.class, i);

            //2.API call
            Response response =sendRegisterRequest(payload);
            
            //Assert.assertNotNull(response);

            

            //3.Status Code validation
            
            Assert.assertEquals(response.getStatusCode(), 201);
            
            
            //4.Extract token and values
            String token = response.jsonPath().getString("token");
            
            //Adjust based on API response Structure
            String userId = response.jsonPath().getString("user.id");
           
            String email = payload.getEmail();
            
           //4.Store in session object
            EventUserSession session = new EventUserSession();
            session.setToken(token);
            session.setUserId(userId);
            session.setEmail(email);

            userMap.put("user" + i, session);
            
            //System.out.println("User " + i + " created: " + token);
            
            System.out.println("Created user " + i +" | ID: " + userId + " | Email: " + email);
        }

        ScenarioContext.getInstance().set("userMap", userMap);

        return userMap;
        
    }
    
    
    public static Map<String, EventUserSession> getmultipleusers() {
    	
    	@SuppressWarnings("unchecked")
		Map<String, EventUserSession> map =(Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");
    	
    	return map;
    	 
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
    
    public static void multipleLoginDetails() throws Exception {
    	
    	 JsonNode users = TestDataLoader.loadTestDatafor_Get("REGISTER");

    	List<String> tokens = new ArrayList<>();

    	for (int i = 0; i < users.size(); i++) {

    	    JsonNode user = users.get(i);

    	    LoginRequestpojo loginPayload = new LoginRequestpojo();
    	    
    	    loginPayload.setEmail(user.get("email").asText());
    	    
    	    loginPayload.setPassword(user.get("password").asText());

    	    Response response = RegisterRequest.logindetails(loginPayload);
    	    
    	    //Assert.assertEquals(response.getStatusCode(), 201);

    	    String token = response.jsonPath().getString("token");
    	    tokens.add(token);

    	    System.out.println("TOKEN: " + token);
    	}
    }
    
    
    //------------------
    //Single user only
    //--------------------
// Post  register validation
    public static Response sendRegisterRequest(RegisterRequestpojo payload) {

        //Assert.assertNotNull(payload, "Payload is NULL");

        RequestSpecification req = SpecificationClass.requestHeaderWithoutToken();

        return RestAssured.given()
                .spec(req)
                .body(payload)
                .when()
                .post(APIResources.REGISTER.getResource())
                .then()
                .extract()
                .response();
    }
	
    
	
//stored token validation
	public static String extractAndValidateToken(Response response) {

		RegisterRequestResponse pojo = response.as(RegisterRequestResponse.class);

        String token = pojo.getToken();

        System.out.println("TOKEN = " + token);

        Assert.assertNotNull(token);

        context.set("eventtoken", token);

        return token;
    }

 //Post login validation
	public static Response logindetails(LoginRequestpojo payload) {
		
		RequestSpecification reqlog = SpecificationClass.requestHeaderWithoutToken();
	      
	      String endpoint1 =APIResources.POSTLOGIN.getResource();

	     Response   Response1 = RestAssured.given()
	                .spec(reqlog)
	                .log().all()
	                .body(payload)
	                .when()
	                .post(endpoint1)
	                .then()
	                .log().all()
	                .extract()
	                .response();

	        return Response1;
	}

	//Getting userid
	public static String getuserid(Response response1) {
		
		LoginResponse pojo = response1.as(LoginResponse.class);

		String userid = pojo.getUser().getId();
		
		String userId = response1.jsonPath().getString("user.id");
		
		String email = response1.jsonPath().getString("user.email");

        System.out.println("id = " + userid);

        Assert.assertNotNull(userid);

        context.set("userid", userid);

        return userid;
		
	}
	
	//Gettinguseremail

	public static String getuseremail(Response response1) {
		
		LoginResponse pojo = response1.as(LoginResponse.class);

        String useremail = pojo.getUser().getEmail();

        System.out.println("email = " + useremail);

        Assert.assertNotNull(useremail);

        context.set("useremail", useremail);

        return useremail;
		
	}
	
	//Get request details
	
	public static Response getUserDetails(int userNumber) {

		
		Map<String, String> tokenMap = (Map<String, String>) ScenarioContext.getInstance().get("tokenMap");

	    String token = tokenMap.get("user" + userNumber);
	   
	    //RequestSpecification reqget = SpecificationClass.requestHeaderWithToken();

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

	
}
	