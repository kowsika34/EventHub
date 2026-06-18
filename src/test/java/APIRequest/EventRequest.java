package APIRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import CommonUtils.TestDataLoader;
import CommonUtils.querydefaults;
import Context.EventUserSession;
import Context.ScenarioContext;
import Context.SpecificationClass;
import Context.UserSession;
import Pojo.Eventpojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import EnumClass.APIResources;

public class EventRequest {

	static Response response;
	
	public static Response createEvent(Eventpojo payload,String token) {
		
		RequestSpecification req = SpecificationClass.requestHeaderWithToken(token);
		
		String endpoint = APIResources.POSTEVENT.getResource();
		
		
			response = RestAssured.given()
					.spec(req)
					.body(payload)
					.when()
					.post(endpoint)
					.then()
					.extract()
					.response();
		
		
     return response;
		
		
	}
	
	
	public static void eventcreatemutipleusers() throws Exception {

	    @SuppressWarnings("unchecked")
	    Map<String, EventUserSession> users = (Map<String, EventUserSession>)ScenarioContext.getInstance().get("userMap");

	    Map<String, Response> eventResponses = new HashMap<>();

	    int i = 0;

	    for (Map.Entry<String, EventUserSession> entry : users.entrySet()) {

	        EventUserSession user = entry.getValue();

	        // Different payload for each user
	        Eventpojo payload = TestDataLoader.loadTestDatafor_Post_Put( "createevent", Eventpojo.class,i );

	        Response response =createEvent(payload, user.getToken());

	        eventResponses.put( entry.getKey(), response );

	        String eventId = response.jsonPath().getString("data.id");

	        //user.setEventId(eventId); //update session
	        
	        System.out.println(response.asPrettyString());

	        //Debugging pharse int
	        String eventId1 = response.jsonPath().getString("data.id");

	        System.out.println("Extracted Event ID = " + eventId1);

	        Assert.assertNotNull("Event ID is NULL in response", eventId1);
	        
	        EventUserSession userSession = entry.getValue();

	        userSession.setEventId(Integer.parseInt(eventId));

	        //EventUserSession.setEventId(Integer.parseInt(eventId1));
	        
	        user.setEventId(Integer.parseInt(eventId));

	        System.out.println(entry.getKey()+ " CREATED EVENT = " + payload.getTitle());

	        i++;
	    }
	    
	 // 🔥 IMPORTANT: update ScenarioContext with modified users
	    ScenarioContext.getInstance().set("userMap", users);

	    ScenarioContext.getInstance().set("eventResponses", eventResponses);
	}
	
	public static void event_id() {

	    @SuppressWarnings("unchecked")
	    Map<String, Response> eventResponses =
	            (Map<String, Response>) ScenarioContext.getInstance().get("eventResponses");

	    Map<String, Integer> eventIdMap = new HashMap<>();

	    for (Map.Entry<String, Response> entry : eventResponses.entrySet()) {

	        Response response = entry.getValue();

	        System.out.println(entry.getKey() + " EVENT RESPONSE:");
	        System.out.println(response.asPrettyString());

	        String eventId = response.jsonPath().getString("data.id");

	        Assert.assertNotNull("Event ID is NULL", eventId);

	        eventIdMap.put(
	                entry.getKey(),
	                Integer.parseInt(eventId)
	        );

	        System.out.println(entry.getKey() + " EVENT ID = " + eventId);
	    }

	    ScenarioContext.getInstance().set("eventIdMap", eventIdMap);
	}
	
	public static void event_title() {

	    @SuppressWarnings("unchecked")
	    Map<String, Response> eventResponses =ScenarioContext.getInstance().get("eventResponses");

	    for (Map.Entry<String, Response> entry : eventResponses.entrySet()) {

	        Response response = entry.getValue();

	        // Adjust JSON path if needed
	        String title = response.jsonPath().getString("data.title");

	        // fallback
	        if (title == null) {
	            title =  response.jsonPath().getString("data.title");
	        }

	        Assert.assertNotNull(title);

	        System.out.println( entry.getKey() +" EVENT TITLE = " + title );
	    }
	    
	} 
	    public void status_code_validation(Integer expectedStatusCode) {

	        @SuppressWarnings("unchecked")
	        Map<String, Response> eventResponses = ScenarioContext.getInstance().get("eventResponses");

	        for (Map.Entry<String, Response> entry : eventResponses.entrySet()) {

	            Response response = entry.getValue();

	            int actualStatusCode = response.getStatusCode();

	            System.out.println(entry.getKey() +" STATUS CODE = " +actualStatusCode);

	            Assert.assertEquals( expectedStatusCode.intValue(),actualStatusCode);
	        }
	    }
	
	  
	    public void status_line_validation(String expectedStatusLine) {

	        @SuppressWarnings("unchecked")
	        Map<String, Response> eventResponses =ScenarioContext.getInstance().get("eventResponses");

	        for (Map.Entry<String, Response> entry :eventResponses.entrySet()) {

	            Response response = entry.getValue();

	            String actualStatusLine =response.getStatusLine();

	            System.out.println(entry.getKey() +" STATUS LINE = " +actualStatusLine);

	            Assert.assertEquals(expectedStatusLine,actualStatusLine);
	        }
	    }
	    
	    
	   
	    public void success_message() {

	        @SuppressWarnings("unchecked")
	        Map<String, Response> eventResponses = (Map<String, Response>) ScenarioContext.getInstance() .get("eventResponses");

	        for (Map.Entry<String, Response> entry : eventResponses.entrySet()) {

	            Response response = entry.getValue();

	            String message =response.jsonPath().getString("message");

	            Assert.assertEquals("Event created successfully",  message);
	        }
	    }
	    
	    
	    
	    public static Response usersendsget(String token) {

	    	
	    	
	        RequestSpecification req = SpecificationClass.requestHeaderWithToken(token);

	        String endpoint= APIResources.GETALLEVENT.getResource();

	        System.out.println("Endpoint = " + endpoint);
	       
	        System.out.println("Token = " + token);

	        
	        response = RestAssured.given()
	                .spec(req)
	                .when()
	                .queryParam("category", querydefaults.category)
	                .queryParam("city", querydefaults.city)
	                .queryParam("page", querydefaults.page)
	                .queryParam("limit",querydefaults.limit)
	                .get(endpoint)
	                .then()
	                .extract()
	                .response();
	         
	        System.out.println(response.asPrettyString()); // DEBUG


	        return response;
	    }
	    
	 
	    public static void getallevents(Response response) {
	    	
	    	
	    	  Assert.assertNotNull("Response is NULL", response);
	    	    
	    	    System.out.println(response.asPrettyString());


	    	    List<Object> events = response.jsonPath().getList("data");

	    	    Assert.assertNotNull("Events is NULL", events);
	    	    Assert.assertNotNull(events);

	    	    System.out.println("Total Events = " + events.size());

	    	    if (events.isEmpty()) {
	    	        System.out.println("No events returned for given filters");
	    	    }

	    	    System.out.println("Total Events = " + events.size());
	    	    
	    	    if(events.isEmpty()) {
	    	       
	    	    	System.out.println("No events returned for current filters");
	    	    }
	    	

	     
	    }
	    
	    public static Response getEventById(int eventId) {
	    	
	    	@SuppressWarnings("unchecked")
			Map<String, EventUserSession> userMap = (Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");
	    	

	    	@SuppressWarnings("unchecked")
	    	Map<String, Integer> eventIdMap =(Map<String, Integer>) ScenarioContext.getInstance().get("eventIdMap");

	        Assert.assertNotNull("eventIdMap is NULL", eventIdMap);
	        
	        System.out.println(userMap.get("user0").getClass());
	       
	        System.out.println(eventIdMap.get("user0").getClass());

	        EventUserSession user = userMap.get("user0");
	       
	        Integer eventId1 = eventIdMap.get("user0");
	        
	        Assert.assertNotNull("user0 not found",eventIdMap);

	        String token = user.getToken();

	        Assert.assertNotNull("token is NULL", token);

	        System.out.println("EventId = " + eventId1);
	        System.out.println("Token = " + token);

	        RequestSpecification req =
	            SpecificationClass.requestHeaderWithToken(token);

	        String endpoint =APIResources.GETEVENTBYID.getResource() + "/" + eventId1;
 
	        System.out.println("Endpoint = " + endpoint);

	        Response response = RestAssured.given()
	                .spec(req)
	                .param("id", eventId)
	                .when()
	                .get(endpoint)
	                .then()
	                .extract()
	                .response();

	        System.out.println(response.asPrettyString());

	        return response;
	        
	         }
	    
	    
	    public static Response userupdateseventwithid() throws Exception {

        @SuppressWarnings("unchecked")
		Map<String, EventUserSession> userMap = (Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");
	    	

	    	@SuppressWarnings("unchecked")
	    	Map<String, Integer> eventIdMap =(Map<String, Integer>) ScenarioContext.getInstance().get("eventIdMap");

	        Assert.assertNotNull("eventIdMap is NULL", eventIdMap);
	        
	        System.out.println(userMap.get("user0").getClass());
	       
	        System.out.println(eventIdMap.get("user0").getClass());

	        EventUserSession user = userMap.get("user0");
	       
	        Integer eventId1 = eventIdMap.get("user0");
	        
	        Assert.assertNotNull("user0 not found",eventIdMap);

	        String token = user.getToken();

	        Assert.assertNotNull("token is NULL", token);

	        System.out.println("EventId = " + eventId1);
	        System.out.println("Token = " + token);

	        // Load update payload (NO hardcode)
	        Eventpojo payload =TestDataLoader.loadTestDatafor_Post_Put("UPDATEEVENT", Eventpojo.class, 0);

	        response = EventRequest.updateEventById(eventId1, payload, token);
	        
	        return response;
	    }


		public static Response updateEventById(int eventId, Eventpojo payload, String token) {

		    RequestSpecification req = SpecificationClass.requestHeaderWithToken(token);

		    String endpoint =APIResources.PUTEVENTBYID.getResource() + "/" + eventId;
		   
		    Response response = RestAssured.given()
		            .spec(req)
		            //.pathParam("id", eventId)
		            .body(payload)
		            .when()
		            .put(endpoint)
		            .then()
		            .extract()
		            .response();

		    System.out.println(response.asPrettyString());

		    return response;
		}
		
		
		public static Response deleteEventById() {

		    Map<String, Integer> eventIdMap =
		        (Map<String, Integer>) ScenarioContext.getInstance().get("eventIdMap");

		    Assert.assertNotNull("eventIdMap is null", eventIdMap);

		    Integer eventId = eventIdMap.get("user0");
		    Assert.assertNotNull("eventId not found for user0", eventId);

		    Map<String, EventUserSession> userMap =
		    	    (Map<String, EventUserSession>) ScenarioContext.getInstance().get("userMap");

		    	EventUserSession user = userMap.get("user0");

		    	String token = user.getToken();

		    	Object obj = ScenarioContext.getInstance().get("userMap");

		    	System.out.println(obj);

		    	for (Map.Entry<?, ?> e : ((Map<?, ?>) obj).entrySet()) {
		    	    System.out.println(
		    	        e.getKey() + " -> " +
		    	        e.getValue().getClass().getName()
		    	    );
		    	}
		    	
		    	
		    Assert.assertNotNull("userMap is null", userMap);

		   // String token = userMap.get("user0").getToken();
		    Assert.assertNotNull("token not found for user0", token);

		    RequestSpecification req =
		        SpecificationClass.requestHeaderWithToken(token);

		    String endpoint =
		        APIResources.DELETEEVENTBYID.getResource() + "/" + eventId;

		    return RestAssured.given()
		            .spec(req)
		            .when()
		            .delete(endpoint)
		            .then()
		            .extract()
		            .response();
		}
		
		public void responsestatusshouldbe(Integer expectedStatusCode) {

		    Assert.assertNotNull("Response is NULL", response);

		    Assert.assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
		}
		
		
		
		public static void responseshouldcontaindeletesuccessmessage() {

		    String message = response.jsonPath().getString("message");

		    Assert.assertEquals( "Event deleted successfully", message);
		}
		
}
