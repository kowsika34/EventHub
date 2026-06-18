package Context;


public class TestContext {
	
	
	//private static final ScenarioContext context = ScenarioContext.getInstance();
	
	private static final ScenarioContext context = ScenarioContext.getInstance();
	
	public static void setToken(String token) {
		
		context.set(ContextKeyEnum.eventtoken.name(),token);
		

	}
	
    public static void setuserid(int id) {
		
		context.set(ContextKeyEnum.userid.name(), id);
	}
    
    public static void setuseremail(String email) {
		
		context.set(ContextKeyEnum.useremail.name(), email);
	}
    
    public static void seteventusers(int id) {
    	
    	context.set(ContextKeyEnum.eventusers.name(),id);
    }

}

