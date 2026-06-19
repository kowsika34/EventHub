package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@RunWith(Cucumber.class)

@CucumberOptions(features="src/test/resources/features", glue = {"StepDefinitions"}, plugin = {"pretty", "html:target/report.html"})

public class Testrunner extends AbstractTestNGCucumberTests{
	
	
	

}
