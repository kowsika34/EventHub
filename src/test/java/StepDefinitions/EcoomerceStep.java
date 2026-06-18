package StepDefinitions;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import APIRequest.BookingRequest;
import APIRequest.EventRequest;
import APIRequest.LoginRequest;
import APIRequest.RegisterRequest;
import CommonUtils.ConfigReader;
import Context.EventUserSession;
import Context.ScenarioContext;
import Context.SpecificationClass;
import Context.UserSession;
import Pojo.LoginRequestpojo;
import Pojo.RegisterRequestpojo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.Registerpayload;


public class EcoomerceStep extends SpecificationClass   {
	
	Response response;
	Response response1;
	//Response response2;
	RegisterRequestpojo registerPayload;
	LoginRequestpojo registerPayload1;
	RequestSpecification req;
    

    ScenarioContext context = ScenarioContext.getInstance();

    
    public EcoomerceStep() throws Exception {
        
    	req = reqspec; // from SpecificationClass Utils/Base class 
    }

   //BASEURL
    
	@Given("User has base URI")
	public void user_has_base_uri() {
	   
		// already handled in requestSpecification()
		System.out.println("BASE URI = " + ConfigReader.getProperty("baseURI"));
		
	}

	@Then("User has valid authentication token")
	public void user_has_valid_authentication_token() {
	    
		System.out.println("LOG = " + log);
		
		System.out.println("SPEC = " + SpecificationClass.requestHeaderWithoutToken());
	}
	//--------------------------
	//Prepare Payload
	//Mutiple users gherkin
	//-----------------------------
	
	@Given("User prepares registration payload")
	public void user_prepares_registration_payload() throws Exception {
	    
		System.out.println("Preparing payload");
	}

	@When("User sends POST request for all users")
	public void user_sends_post_request_for_all_users() throws Exception {

		response = RegisterRequest.registerAllUsers();

	}

	@Given("User prepares registration payload for user {int}")
	public void user_prepares_registration_payload_for_user(int index) throws Exception {
	   
		registerPayload =Registerpayload.getRegisterPayload(index);
		
		
		
		//Single user only	
		//registerPayload = TestDataLoader.loadTestDatafor_Post_Put("REGISTER", RegisterRequestpojo.class);

	   
    }
//---------------------
	//countingusers
	//Prepare POST Request
//-----------------------------
	@When("User sends POST request for {int} users")
	public void user_sends_post_request_for_users(int users) throws Exception {
	   
		
		Map<String, EventUserSession> userMap = RegisterRequest.registerMultipleUsers(users);

	    ScenarioContext.getInstance().set("userMap", userMap);

	    System.out.println("Users created = " + userMap.size());
		
		
	
		//Single user only
		//response = RegisterRequest.sendRegisterRequest(registerPayload);
		//context.set("RegisterResponse", response);
		
		
}
//Single user only need response
	//Prepare StatusCode
	@When("Response status code should be {int}")
	public void response_status_code_should_be(Integer statusCode) {
	    
		//System.out.println(response.getStatusCode());

	    //Assert.assertEquals(response.getStatusCode(),statusCode.intValue());
		

	}
   //Prepare StatusLine
	@Then("Response body is {string}")
	public void response_body_is(String expected) {
		
		
		//RegisterRequestResponse pojo = response.as(RegisterRequestResponse.class);

		//Assert.assertTrue(response.getStatusLine().contains("Created"));
		
		//System.out.println(response.getStatusLine());
		
	}
//Sucess Validation
	@Then("Response should contain success as true")
	public void response_should_contain_success_as_true() {
	    
		//RegisterRequestResponse pojo =response.as(RegisterRequestResponse.class);

       // assertTrue(pojo.isSuccess());
		
	}
//Token Validation
	@SuppressWarnings("unchecked")
	@Then("Response should contain token")
	public void response_should_contain_token() {
	    //--------------------------
		//Single response validation
		//------------------------------
		 //String token = RegisterRequest.extractAndValidateToken(response);

		// System.out.println("Stored Token = " + token);
		 //---------------------------
		 //multiuser token validation
		//--------------------------------
		Map<String, EventUserSession> userMap = (Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");

		    Assert.assertFalse(userMap.isEmpty());

		    for (String key : userMap.keySet()) {

		        String token = userMap.get(key).getToken();

		        Assert.assertNotNull(token);

		        System.out.println(key + " TOKEN = " + token);
		    }
		
}
//LoginPost validation	
	@Given("User prepares login payload")
	public void user_prepares_login_payload() throws Exception {
	    
		//registerPayload1 =TestDataLoader.loadTestDatafor_Post_Put( "LOGINREQUEST", LoginRequestpojo.class);
		
		System.out.println("Preparing loginpayload");
		
	}

	@When("User send POST request to {string}")
	public void user_send_post_request_to(String string) throws Exception {
	    
		//response1 = RegisterRequest.logindetails(registerPayload1);
		//context.set("LoginResponse", response1);
		
		 LoginRequest. multipleLoginDetails(); 
	}

	@Then("Response should contain authentication token")
	public void response_should_contain_authentication_token() {
	    //---------------
		//Single user
		//-----------------
		 //LoginResponse pojo =response1.as(LoginResponse.class);

	   // String token = pojo.getToken();

	    //System.out.println("AUTH TOKEN = " + token);

	    //Assert.assertNotNull(token);

	    //context.set("logintoken", token);
	    
		//------------------
		//Mutipleuser
		//-------------
	    
		@SuppressWarnings("unchecked")
		List<String> tokens = ScenarioContext.getInstance().get("tokens");

	    System.out.println("TOTAL TOKENS: " + tokens.size());
	    
	    System.out.println(tokens);
		
	}

	@When("Responses status code should be {int}")
	public void responses_status_code_should_be(int expectedStatus) {
	    
		//System.out.println(response.getStatusCode());

	    //Assert.assertEquals(response.getStatusCode(),statusCode.intValue());
	    
	   // Response response =ScenarioContext.getInstance().get("response");

	       // Assert.assertEquals(expectedStatus, response.getStatusCode());
		
	}

	@Then("Response should contain user details")
	public void response_should_contain_user_details() {
	    
		String userid = LoginRequest.getuserid(response);

		 System.out.println("Stored id = " + userid);
		 
		 String useremail = LoginRequest.getuseremail(response);

		 System.out.println("Stored email = " + useremail);
		
	}
//Get details using id
	@When("User sends GET request to {string}")
	public void user_sends_get_request_to(String resource) {
	    
		//---------
		//Single user
		//----------
	    
	   // Response response = RegisterRequest.getUserDetails();

	   // ScenarioContext.getInstance().set("GetResponse", response);
	    
		
		//------
		//Mutiple user
		//------------------
	    Map<String, Response> responseMap = LoginRequest.getMultipleUsersDetails();

	    ScenarioContext.getInstance().set("userResponses", responseMap);
		
	}

	@Then("Response status codes should be {int}")
	public void response_status_codes_should_be(int code) {
	    
		//System.out.println(response2.prettyPrint());

	   // Assert.assertEquals(response2.getStatusCode(), 200);
	    
		//-----------
		//Mutiple user
		//-------------------
	
		@SuppressWarnings("unchecked")
		Map<String, Response> responseMap = (Map<String, Response>) ScenarioContext.getInstance().get("userResponses");

		    for (String key : responseMap.keySet()) {

		       Response response = responseMap.get(key);

		        Assert.assertEquals(response.getStatusCode(), code);

		        System.out.println(key + " validated with status " + code);
		    }
		
	}

	@SuppressWarnings("unchecked")
	@When("Response should contains user email")
	public void response_should_contains_user_email() {
	    
		//String email = response2.jsonPath().getString("user.email");

	   // System.out.println("USER EMAIL = " + email);

	   // Assert.assertNotNull(email);
	    
	    //---------
		//mutipleuser
		//------------------
		 @SuppressWarnings( "unchecked")
		
		 Map<String, Response> responseMap = ScenarioContext.getInstance().get("userResponses");

			    for (Map.Entry<String, Response> entry : responseMap.entrySet()) {

			        String email = entry.getValue() .jsonPath() .getString("user.email");

			        Assert.assertNotNull(email);

			        System.out.println(entry.getKey() + " email = " + email);
			    }
	}

	@Then("Response should contain user id")
	public void response_should_contain_user_id() {
	    
		//String id = response2.jsonPath().getString("user.userId");

	    //System.out.println("USER ID = " + id);

	    //Assert.assertNotNull(id);

	   // context.set("userid", id);
		
		//----------
		//Mutiple user
		//-------------------
	    
		 Map<String, Response> responseMap = ScenarioContext.getInstance().get("userResponses");

		    for (Map.Entry<String, Response> entry : responseMap.entrySet()) {

		        Response response = entry.getValue();

		        System.out.println(entry.getKey() + " RESPONSE:");
		       
		        System.out.println(response.asPrettyString());

		        // Try correct possible paths
		        
		        String id = response.jsonPath().getString("user.userId");

		        // fallback if API is flat structure
		        if (id == null) {
		          
		        	id = response.jsonPath().getString("id");
		        }

		        System.out.println(entry.getKey() + " id = " + id);

		        Assert.assertNotNull( "User ID is NULL for " + entry.getKey(),id);
		    }
		    
	}
	////////////////////////////////////////
	/// Events Created /////////
	///////////////////////////////////

	@Given("User prepares event payload")
	public void user_prepares_event_payload() {
	    
		System.out.println("Prepare event payload");
	}

	@When("User sends POST request to {string}")
	public void user_sends_post_request_to(String string) throws Exception {
	    
		EventRequest.eventcreatemutipleusers();
	}

	@Then("Response should contain event id")
	public void response_should_contain_event_id() {
 
        EventRequest.event_id();
		
	}

	@Then("Response should contain event title")
	public void response_should_contain_event_title() {

          EventRequest.event_title();
		
	}
	
	/////////////////////////////
	/// Get All Events////////
	////////////////////////
	
	@When("User send GET request to {string}")
	public void user_send_get_request_to(String token) {
	    
		@SuppressWarnings("unchecked")
	    Map<String, EventUserSession> userMap =
	        (Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");

	    String token1 = userMap.get("user0").getToken();

	    response = EventRequest.usersendsget(token1);

	    System.out.println("GET Response stored = " + response);
		
	}

	@Then("Response the status code should be {int}")
	public void response_the_status_code_should_be(Integer expectedStatusCode) {
		
		Assert.assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
	}

	@Then("Response should contain event list")
	public void response_should_contain_event_list() {
	   
		
		EventRequest.getallevents(response);
		
		System.out.println("Response after GET = " + response);
	}

	/////////////////////////
	////Get By id///////
	///////////////////////////
	@Given("User send GET request to  att {string}")
	public void user_send_get_request_to_att(String key) {
		
		
		Map<String, Integer> eventIdMap = (Map<String, Integer>) ScenarioContext.getInstance().get("eventIdMap");

		    Assert.assertNotNull("eventIdMap is NULL", eventIdMap);

		    Integer eventId = eventIdMap.get("user0");

		    Assert.assertNotNull("eventId is NULL", eventId);

		    response = EventRequest.getEventById(eventId);
		//response = EventRequest.getEventById(eventId);
		 
		

	}


   @When("Response status code should be at {int}")
	public void response_status_code_should_be_at(Integer int1) {
	    
	   
	   System.out.println("STATUS CODE = " + response.getStatusCode());
	   System.out.println("RESPONSE = " + response.asPrettyString());
	   System.out.println("CONTENT TYPE = " + response.getContentType());
		
	}

	@Then("Response should contain  at event id")
	public void response_should_contain_at_event_id() {

		Map<String, Integer> eventIdMap = (Map<String, Integer>) ScenarioContext.getInstance().get("eventIdMap");

		    Integer eventId = eventIdMap.get("user0");

		    Assert.assertNotNull(eventId);

		    System.out.println("Validating eventId = " + eventId);

		
	}

	@Then("Response should contain  at event title")
	public void response_should_contain_at_event_title() {
		
		Assert.assertNotNull("Response is NULL", response);

	    System.out.println("STATUS = " + response.getStatusCode());
	    System.out.println("BODY = " + response.asPrettyString());

	    Assert.assertTrue(
	        "Response is not JSON",
	        response.getContentType().contains("application/json")
	    );

	    String title = response.jsonPath().getString("data.title");

	    Assert.assertNotNull(title);
	    Assert.assertFalse(title.trim().isEmpty());

	    System.out.println("Event Title = " + title);
	
	}
	
	//////////////////////////
	///Update Event by id///
	////////////////////////////

	@Given("User creates event for multiple users")
	public void user_creates_event_for_multiple_users() {
	    
		
		
		
	}

	@When("User updates event with id")
	public void user_updates_event_with_id() throws Exception {

	response =EventRequest.userupdateseventwithid();
	
	System.out.println("UPDATE RESPONSE = " + response);
		
	}

	@Then("Response status should be {int}")
	public void response_status_should_be(Integer expectedStatusCode) {


		 Assert.assertNotNull("Response is NULL", response);

		    Assert.assertEquals(expectedStatusCode.intValue(),
		                        response.getStatusCode());
		
	}

	@Then("Response should contain updated event title")
	public void response_should_contain_updated_event_title() {

		
		Assert.assertNotNull(response);

	    String title = response.jsonPath().getString("data.title");

	    Assert.assertNotNull(title);
	    
	    Assert.assertFalse(title.isEmpty());

	    System.out.println("Updated Title = " + title);

		
	}
	////////////////////////////
	///Delete Event by id////
	///////////////////////
	
	@Given("Event id is available")
	public void event_id_is_available() {
		
		  System.out.println("eventIdMap = " +
			        ScenarioContext.getInstance().get("eventIdMap"));

			    Map<String, Integer> eventIdMap =
			        (Map<String, Integer>) ScenarioContext.getInstance().get("eventIdMap");

			    Assert.assertNotNull("eventIdMap not found", eventIdMap);

			    Integer eventId = eventIdMap.get("user0");

			    Assert.assertNotNull("eventId not found for user0", eventId);

			    response = EventRequest.deleteEventById();
		
	}
	
	@When("User sends DELETE request to {string}")
	public void user_sends_delete_request_to(String string) {

         // EventRequest.userdeleteseventwithid();
          
          
   }

	@Then("Response status code should be  at {int}")
	public void response_status_code_should_be_at_(Integer expectedStatusCode) {

		
		 Assert.assertNotNull("Response is NULL", response);

		    Assert.assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
 
	}


	
	@Then("Response should contain cancellation message")
	public void response_should_contain_cancellation_message() {

		String message = response.jsonPath().getString("message");

	    Assert.assertEquals( "Event deleted successfully", message);

	}

  ////////////////////////////////////////////////////////
  /// Booking Details
  ////////////////////////////////////////////////////
	
	@Given("User prepares payload")
	public void user_prepares_payload() {
	   
		System.out.println("Preparing payload");
	}

	@When("User sends post requested to send {string}")
	public void user_sends_post_requested_to_send(String string) {

     // BookingRequest.Bookingcreate();
		
	}

	@When("User got Response code should be {int}")
	public void user_got_response_code_should_be(Integer int1) throws Exception {

      BookingRequest.createmutiplebooking();
      
      
     
		
	}

	@Then("User response should contain event id")
	public void user_response_should_contain_event_id() {
          @SuppressWarnings("unchecked")
	    	    Map<String, Response> bookingResponses = (Map<String, Response>) ScenarioContext.getInstance().get("BookingResponse");

	    	    for (Map.Entry<String, Response> entry : bookingResponses.entrySet()) {

	    	        Integer eventId = entry.getValue().jsonPath().getInt("data.eventId");

	    	        Assert.assertNotNull(eventId);

	    	        System.out.println(entry.getKey() + " EVENT ID = " + eventId);
	    	    }
		

	}

	@Then("User response should contain bookingref")
	public void user_response_should_contain_bookingref() {
        
		 @SuppressWarnings("unchecked")
		Map<String, EventUserSession> userMap = (Map<String, EventUserSession>) ScenarioContext.getInstance() .get("userMap");

			    for (Map.Entry<String, EventUserSession> entry : userMap.entrySet()) {

			        String bookingRef = entry.getValue().getBookingRef();

			        Assert.assertNotNull(bookingRef);

			        System.out.println( entry.getKey() + " BOOKING REF = " + bookingRef);
			    }

		
	}

	@Then("User response should contain bookingid")
	public void user_response_should_contain_bookingid() {


		@SuppressWarnings("unchecked")
		Map<String, EventUserSession> userMap = (Map<String, EventUserSession>) ScenarioContext.getInstance() .get("userMap");

		    for (Map.Entry<String, EventUserSession> entry : userMap.entrySet()) {

		        Integer bookingId = entry.getValue().getBookingId();

		        Assert.assertNotNull(bookingId);

		        System.out.println( entry.getKey() + " BOOKING ID = " + bookingId);
		    }
		
		
	}
	////////////////////////////////////////////////
	////GETTING ALL BOOKS ////////
	///////////////////////////////
	
	@When("User send get request too getallbooking")
	public void user_send_get_request_too_getallbooking() {
		
		@SuppressWarnings("unchecked")
	    Map<String, EventUserSession> users =
	        (Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");

	    EventUserSession user = users.get("user0");

	    response = BookingRequest.usersendgetbook(
	            user.getToken(),
	            user.getEventId());
		
		
	}

	@Then("Response should be {int} at statuscode")
	public void response_should_be_at_statuscode(Integer expectedStatusCode) {
	   
		 Assert.assertNotNull("Response is NULL", response);

		    BookingRequest.getallbooks(response);

		    Assert.assertEquals(expectedStatusCode.intValue(),
		                        response.getStatusCode());
		
	}

	@Then("Response contain pagination list")
	public void response_contain_pagination_list() {
		
		
		System.out.println("Total BOOks =" + response);


		
	}
	////////////////////////////////////////
	/// GEt book by id////
	/////////////////////////////////////


	@When("User send get request to get book by {string}")
	public void user_send_get_request_to_get_book_by(String string) {
	   
		
		Map<String, Integer> bookIdMap = (Map<String, Integer>) ScenarioContext.getInstance().get("bookIdMap");

	    Assert.assertNotNull("bookIdMap is NULL", bookIdMap);

	    Integer bookId = bookIdMap.get("user0");

	    Assert.assertNotNull("bookId is NULL", bookId);

	    response = BookingRequest.gettallbooksbyid(bookId);
	    
		
		
	}

	@Then("Response should be statuscodes at {int}")
	public void response_should_be_statuscodes_at(Integer int1) {
	   
		
		
		 System.out.println("STATUS CODE = " + response.getStatusCode());
		   System.out.println("RESPONSE = " + response.asPrettyString());
		   System.out.println("CONTENT TYPE = " + response.getContentType());
	}

	@Then("Response should be contain booking id")
	public void response_should_be_contain_booking_id() {
	    
		Map<String, Integer> bookIdMap = (Map<String, Integer>) ScenarioContext.getInstance().get("bookIdMap");

	    Integer bookId = bookIdMap.get("user0");

	    Assert.assertNotNull(bookId);

	    System.out.println("Validating eventId = " + bookId);
	}

	////////////////////////////////
	/// Get book by ref//////////
	//////////////////////////////

	@When("User send get request book by {string}")
	public void user_send_get_request_book_by(String string) {
	   
		

		Map<String, String> bookRefMap = (Map<String, String>) ScenarioContext.getInstance().get("bookRefMap");

	    Assert.assertNotNull("bookRefMap is NULL",bookRefMap);

	    String bookingRef = bookRefMap.get("user0");

	    Assert.assertNotNull("bookRef is NULL", bookingRef);

	    response = BookingRequest.gettallbooksbyref(bookingRef);
	}

	@Then("Response should be statuscode att- {int}")
	public void response_should_be_statuscode_att(Integer int1) {
	   
		System.out.println("STATUS CODE = " + response.getStatusCode());
		   System.out.println("RESPONSE = " + response.asPrettyString());
	}

	@Then("Response should be contain booking Ref")
	public void response_should_be_contain_booking_ref() {
	   
		Map<String, String> bookRefMap = (Map<String, String>) ScenarioContext.getInstance().get("bookRefMap");

	    String bookRef = bookRefMap.get("user0");

	    Assert.assertNotNull(bookRef);

	    System.out.println("Validating eventId = " + bookRef);
	}

	@Given("User send Bookig id")
	public void user_send_bookig_id() {
	    
		 System.out.println("bookIdMap = " +
			        ScenarioContext.getInstance().get("bookIdMap"));

		Map<String, Integer> bookIdMap =
			   (Map<String, Integer>) ScenarioContext.getInstance().get("bookIdMap");

			    Assert.assertNotNull("bookIdMap not found", bookIdMap);

			    Integer bookIdMap1 = bookIdMap.get("user0");

			    Assert.assertNotNull("bookIdMap not found for user0", bookIdMap1);

			   
		
		
	}

	@When("User send Delete request to cancel the booking")
	public void user_send_delete_request_to_cancel_the_booking() {
	    
		response = BookingRequest.deleteBookingbyid();
	}

	@Then("Response should be {int}")
	public void response_should_be(Integer int1) {
	 
		response = BookingRequest.responsestatus(int1);
		
	}

	@Then("Response should contain booking cancelled message")
	public void response_should_contain_booking_cancelled_message() {
	    
		response = BookingRequest.containdeletesuccessmessage();
	}


	
	
}

