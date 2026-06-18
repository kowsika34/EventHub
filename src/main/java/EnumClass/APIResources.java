package EnumClass;
//ENUM Class
//enum is special class in java which has collection of constants and methods

public enum APIResources {
	
	
	REGISTER("/api/auth/register"),
	POSTLOGIN("/api/auth/login"),
	AUTHGETLOGIN("/api/auth/me"),
	POSTEVENT("/api/events"),
	GETALLEVENT("/api/events"),
	GETEVENTBYID("/api/events"),
	PUTEVENTBYID("/api/events"),
	DELETEEVENTBYID("/api/events"),
	POSTBOOK("/api/bookings"),
	GETALLBOOK("/api/bookings"),
	GETALLBOOKBYID("/api/bookings"),
	GETALLBOOKBYREF("/api/bookings/ref"),
	DELETEBOOKING("/api/bookings");
	
	

	private String resource;
	
    APIResources (String resource){   //loading the value of resource
		  
    	this.resource=resource;
	}	
    
    public String getResource() {    //return the value of resource
    	
    	return resource;
    }
   
	
}
