package Context;

import io.restassured.response.Response;

public class EventUserSession {
	
	
	
	    private String email;
	    private String password;

	    private String userId;
	    private String token;
	    
	    private Integer eventId;
	    
	    private String bookingRef;
	    private Integer bookingId;

	    

		private Response registerResponse;
	    private Response loginResponse;
	    private Response getUserResponse;

	    // getters and setters

	    public String getEmail() {
	        return email;
	    }

	    public int getEventId() {
			
	    	return eventId;
		}

		public  void setEventId(int eventId) {
			
			this.eventId = eventId;
		}

		public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getUserId() {
	        return userId;
	    }

	    public void setUserId(String userId) {
	        this.userId = userId;
	    }

	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }

	    public Response getRegisterResponse() {
	        return registerResponse;
	    }

	    public void setRegisterResponse(Response registerResponse) {
	        this.registerResponse = registerResponse;
	    }

	    public Response getLoginResponse() {
	        return loginResponse;
	    }

	    public void setLoginResponse(Response loginResponse) {
	        this.loginResponse = loginResponse;
	    }

	    public Response getGetUserResponse() {
	        return getUserResponse;
	    }

	    public void setGetUserResponse(Response getUserResponse) {
	        this.getUserResponse = getUserResponse;
	    }
	    
	    public String getBookingRef() {
			return bookingRef;
		}

		public void setBookingRef(String bookingRef) {
			this.bookingRef = bookingRef;
		}

		public Integer getBookingId() {
			return bookingId;
		}

		public void setBookingId(Integer bookingId) {
			this.bookingId = bookingId;
		}
	}


