package com.github.kettoleon.succumber.reports.gherkin;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.github.kettoleon.succumber.reports.gherkin.cucumber"},
        plugin = {
                "com.github.kettoleon.succumber.reports.gherkin.SuccumberFormatter:src/test/resources/reports/succumber-reports.json"},
        features = {"src/test/resources/cucumber"}
)
public class SuccumberReportsGherkinAcceptanceTest {

    @BeforeClass
    public static void setupTrace() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.TRACE);
    }
}
