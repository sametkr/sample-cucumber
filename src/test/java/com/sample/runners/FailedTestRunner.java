package com.sample.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/cucumber-report.html",
                "json:target/cucumber.json",
        },
        glue = "com/sample/step_definitions",
        features = "@target/rerun.txt"
)
public class FailedTestRunner {
}
