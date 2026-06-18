package payload;

import CommonUtils.TestDataLoader;
import Pojo.Bookingpojo;
import Pojo.Eventpojo;
import Pojo.LoginRequestpojo;
import Pojo.RegisterRequestpojo;


	public class Registerpayload {
		
		 public static RegisterRequestpojo getRegisterPayload()
		            throws Exception {

		        return getRegisterPayload(0);
		    }


	    public static RegisterRequestpojo getRegisterPayload(int index) throws Exception {

	       // return TestDataLoader.loadTestDatafor_Post_Put( "REGISTER",RegisterRequestpojo.class);
	        

	        return TestDataLoader.loadTestDatafor_Post_Put("REGISTER",RegisterRequestpojo.class,index);
	    }
	    
	    public static LoginRequestpojo getloginPaylod(int index) throws Exception {
	    	
	    	return TestDataLoader.loadTestDatafor_Post_Put("POSTLOGIN", LoginRequestpojo.class,index);
	    }
	    
	  public static Eventpojo getEventpaylod(int index) throws Exception {
	    	
	    	return TestDataLoader.loadTestDatafor_Post_Put("EventCreate", Eventpojo.class, index);
	    }
        
     public static Eventpojo getupdateEventPaylod(int index) throws Exception {
	    	
	    	return TestDataLoader.loadTestDatafor_Post_Put("UPDATEEVENT", Eventpojo.class, 0);
	    }
     
     public static Bookingpojo getBookingpaylod(int index) throws Exception{
		
    	 return  TestDataLoader.loadTestDatafor_Post_Put("BookingEvent", Bookingpojo.class, index);
    	 
     }
     
	}
