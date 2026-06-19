package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = "StepDefinitions",
    plugin = {"pretty", "html:target/cucumber-report.html"},
    monochrome = true
)
public class Testrunner extends AbstractTestNGCucumberTests {
	
}