package Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import CommonUtils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecificationClass {
	
	
	protected static RequestSpecification reqspec;
	ResponseSpecification responseSpec ;
	
	static String baseURI =ConfigReader.getProperty("config.properties");
	static ScenarioContext context= ScenarioContext.getInstance();
	String FilePath = ConfigReader.getProperty("LogFilePath");
	
	
	protected static PrintStream log;
	public SpecificationClass () throws FileNotFoundException 
	{
		
		log =new PrintStream (new FileOutputStream(FilePath, true));
		
	}

	public static RequestSpecification requestHeaderWithoutToken() {
		
		return new RequestSpecBuilder()
	            .setBaseUri(ConfigReader.getProperty("baseURI"))
	            .setContentType(ContentType.JSON)
	            .addFilter(RequestLoggingFilter.logRequestTo(log))
	            .addFilter(ResponseLoggingFilter.logResponseTo(log))
	            .build();
		
	}
	
	public static RequestSpecification requestHeaderWithToken (String token) {
		
		String baseURL = ConfigReader.getProperty("baseURI");
		
		// String token = ScenarioContext.getInstance().get("eventtoken");

		// String token = ScenarioContext.getInstance().get("token");

	    reqspec = new RequestSpecBuilder()
	            .setBaseUri(baseURL)
	            .addHeader("Authorization", "Bearer " + token)
	            .addFilter(RequestLoggingFilter.logRequestTo(log))
	            .addFilter(ResponseLoggingFilter.logResponseTo(log))
	            .setContentType(ContentType.JSON)
	            .build();

	    return reqspec;
	}
		
	
	public ResponseSpecification responsespecbuilder() {
		
		responseSpec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		return responseSpec;
		
	}

}
