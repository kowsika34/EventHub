package APIRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;

import CommonUtils.TestDataLoader;
import CommonUtils.querydefaults;
import Context.EventUserSession;
import Context.ScenarioContext;
import Context.SpecificationClass;
import EnumClass.APIResources;
import Pojo.Bookingpojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BookingRequest {
	
	static Response response;
  
	public static Response Bookingcreate(Bookingpojo payload, String token, int eventId) {

	    System.out.println("BOOKING EVENT ID = " + eventId);

	    payload.setEventId(eventId); // ✅ FIX HERE

	    System.out.println("FINAL EVENT ID SENT = " + payload.getEventId());

	    RequestSpecification req = SpecificationClass.requestHeaderWithToken(token);

	    String endpoint = APIResources.POSTBOOK.getResource();

	    Response response = RestAssured.given()
	            .spec(req)
	            .body(payload)
	            .when()
	            .post(endpoint)
	            .then()
	            .extract()
	            .response();

	    return response;
	}
	
	@SuppressWarnings("unchecked")
	public static void createmutiplebooking() throws Exception {
		
		
		
		Map<String, EventUserSession> Busers = (Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");
		
		Map<String, Integer> bookIdMap = new HashMap<>();
		Map<String, String> bookRefMap = new HashMap<>();
        Map<String, Response> BookingResponse = new HashMap<>();
        
        int  i =0;
        
        for (Map.Entry<String, EventUserSession> entry : Busers.entrySet()) {
        	
        	EventUserSession Buser = entry.getValue();
        	
        	Bookingpojo payload = TestDataLoader.loadTestDatafor_Post_Put("BookingEvent", Bookingpojo.class, i);
        	
        	
        	
        	Response response = Bookingcreate(payload,Buser.getToken(), Buser.getEventId());

        	BookingResponse.put(entry.getKey(), response);

        	String bookingId =
        	        response.jsonPath().getString("data.id");
        	
        	bookIdMap.put(entry.getKey(), Integer.parseInt(bookingId));

        	String bookingRef =
        	        response.jsonPath().getString("data.bookingRef");
        	
        	//Long bookingRef1 = response.jsonPath().getLong("data.bookingRef");
        	
        	bookRefMap.put(entry.getKey(), bookingRef);
        	
        	//Debugging
        	
        	System.out.println("BOOKING RESPONSE:");
        	System.out.println(response.asPrettyString());

        	String bookingId1 = response.jsonPath().getString("data.id");

        	System.out.println("bookingId = " + bookingId1);

        	Assert.assertNotNull("Booking ID is NULL", bookingId1);

        	Buser.setBookingId(Integer.parseInt(bookingId1));
        	
        	////////////
        	Buser.setBookingId(Integer.parseInt(bookingId));

        	Buser.setBookingRef(bookingRef);

        	System.out.println( entry.getKey()+ " BOOKING ID = "+ bookingId);

        	System.out.println(entry.getKey()+ " BOOKING REF = "+ bookingRef);
	        i++;
	    }
	    
	 // 🔥 IMPORTANT: update ScenarioContext with modified users
	    ScenarioContext.getInstance().set("userMap", Busers);

	    ScenarioContext.getInstance().set("BookingResponse", BookingResponse);
	    
	    ScenarioContext.getInstance().set("bookIdMap", bookIdMap);
	    
	    ScenarioContext.getInstance().set("bookRefMap", bookRefMap);
	    
        }
	
	public void statusvalidation(Response response) {
		
		Assert.assertNotNull("Booking response is NULL", response);

		Assert.assertEquals(201,response.getStatusCode());
			
	}
	
	
	public static Response usersendgetbook(String token, int eventId) {

	    RequestSpecification req = SpecificationClass.requestHeaderWithToken(token);
	    
	    //int eventId = (Integer) ScenarioContext.get("eventId");
	    
	    //int id = Integer.parseInt(eventId);

	    String endpoint = APIResources.GETALLBOOK.getResource();
	    
	    System.out.println("Endpoint = " + endpoint);
	       
        System.out.println("Token = " + token);

	    Response response = RestAssured.given()
	            .spec(req)
	            .queryParam("eventId", eventId)
	            .queryParam("status",querydefaults.STATUS_CONFIRMED)
	            .queryParam("page", querydefaults.DEFAULT_PAGE)
	            .queryParam("limit",querydefaults.DEFAULT_LIMIT)
	            .when()
	            .get(endpoint)
	            .then()
	            .extract()
	            .response();

	    return response;
	    
	    
}
	
	public static void getallbooks(Response response) {
		
		Assert.assertNotNull("Response is NULL",response);
		 
		System.out.println(response.asPrettyString());
		
		List<Object> books = response.jsonPath().getList("data");
		
		Assert.assertNotNull("Books is NULL", books);
		Assert.assertNotNull(books);
		
		System.out.println("Total BOOKs =" +books.size());
		
		if(books.isEmpty()) {
			
			System.out.println("No Books returned for given filter");
		}
		
		System.out.println("Total BOOKS =" +books.size());
		
		if(books.isEmpty()) {
			
			System.out.println("No books returned for current filters");
		}
		
}
	
	public static Response gettallbooksbyid(Integer bookId) {
		
		@SuppressWarnings({ "unchecked", "static-access" })
		Map<String, EventUserSession> userMap = (Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");
		@SuppressWarnings("unchecked")
		Map<String, Integer> bookIdMap =(Map<String, Integer>) ScenarioContext.getInstance().get("bookIdMap");
		
		Assert.assertNotNull("bookIdMap is NULL", bookIdMap);
		
		System.out.println(userMap.get("user0").getClass());
		
		System.out.println(bookIdMap.get("user0").getClass());
		
		EventUserSession user = userMap.get("user0");
		
		Integer bookId2 = bookIdMap.get("user0");
		
		Assert.assertNotNull("user0 not found", bookIdMap);
		
		String token = user.getToken();
		
		Assert.assertNotNull("token is NULL", token);
		
		System.out.println("BookId= " +bookId);
        System.out.println("Token = " + token);
        
        RequestSpecification req = SpecificationClass.requestHeaderWithToken(token);
        
        String endpoint = APIResources.GETALLBOOKBYID.getResource() + "/" + bookId;
        
        System.out.println("Endpoint =" + endpoint);
        
        Response response = (Response) RestAssured.given()
        		            .spec(req)
        		            .when()
        		            .get(endpoint)
        		            .then()
        		            .extract()
        		            .response();
        
        System.out.println(response.asPrettyString());

        return response;
        		            
}
	
	
public static Response gettallbooksbyref(String bookingRef) {
		
		@SuppressWarnings({ "unchecked", "static-access" })
		Map<String, EventUserSession> userMap = (Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");
		@SuppressWarnings("unchecked")
		Map<String, String> bookRefMap =(Map<String, String>) ScenarioContext.getInstance().get("bookRefMap");
		
		Assert.assertNotNull("bookRefMap is NULL", bookRefMap);
		
		System.out.println(userMap.get("user0").getClass());
		
		System.out.println(bookRefMap.get("user0").getClass());
		
		EventUserSession user = userMap.get("user0");
		
		String bookingRef2 = bookRefMap.get("user0");
		
		Assert.assertNotNull("user0 not found", bookRefMap);
		
		String token = user.getToken();
		
		Assert.assertNotNull("token is NULL", token);
		
		System.out.println("BookRef= " +bookingRef);
        System.out.println("Token = " + token);
        
        RequestSpecification req = SpecificationClass.requestHeaderWithToken(token);
        
        String endpoint = APIResources.GETALLBOOKBYREF.getResource() + "/" + bookingRef;
        
        System.out.println("Endpoint =" + endpoint);
        
        Response response = (Response) RestAssured.given()
        		            .spec(req)
        		            .when()
        		            .get(endpoint)
        		            .then()
        		            .extract()
        		            .response();
        
        System.out.println(response.asPrettyString());

        return response;
        		            
}

public static Response deleteBookingbyid() {
	
	
	Map<String, Integer> bookIdMap = (Map<String,Integer>) ScenarioContext.getInstance().get("bookIdMap");
	
	Assert.assertNotNull("bookIdMap is null" ,bookIdMap);
	Integer bookId = bookIdMap.get("user0");
	Assert.assertNotNull("bookId not found for user0" ,bookId);
	
	Map<String, EventUserSession> userMap = (Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");
	
	EventUserSession user = userMap.get("user0");
	
	String token = user.getToken();
	
	Assert.assertNotNull("userMap is Null" , userMap);
	
	Assert.assertNotNull("token not found for user0", token);
	
	RequestSpecification req = SpecificationClass.requestHeaderWithToken(token);
	
	String endpoint = APIResources.DELETEBOOKING.getResource() + "/" + bookId;
	
	response = RestAssured.given()
			.spec(req)
			.when()
			.delete(endpoint)
			.then()
			.extract()
			.response();
	
	return response;
	
	}


public static  Response responsestatus(Integer expectedStatusCode) {

    Assert.assertNotNull("Response is NULL", response);

    Assert.assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
	
    return response;
}



public static Response containdeletesuccessmessage() {

    String message = response.jsonPath().getString("message");

    Assert.assertEquals( "Booking cancelled", message);
	
    return response;
}

	

}


