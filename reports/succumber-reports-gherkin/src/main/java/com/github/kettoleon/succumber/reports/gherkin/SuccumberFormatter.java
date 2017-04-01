package com.github.kettoleon.succumber.reports.gherkin;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class SuccumberFormatter implements Formatter, Reporter {

    private static final Logger log = LoggerFactory.getLogger(SuccumberFormatter.class);

    public SuccumberFormatter(){
        log.trace("{} instantiated", SuccumberFormatter.class.getSimpleName());
        //Load DI context and configuration
    }

    public void syntaxError(String s, String s1, List<String> list, String s2, Integer integer) {

    }

    public void uri(String s) {

    }

    public void feature(Feature feature) {

    }

    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    public void examples(Examples examples) {

    }

    public void startOfScenarioLifeCycle(Scenario scenario) {

    }

    public void background(Background background) {

    }

    public void scenario(Scenario scenario) {

    }

    public void step(Step step) {

    }

    public void endOfScenarioLifeCycle(Scenario scenario) {

    }

    public void done() {

    }

    public void close() {

        //Close streams
        //Copy reports-ui file
    }

    public void eof() {

    }



    /* Reporter methods */

    public void before(Match match, Result result) {

    }

    public void result(Result result) {

    }

    public void after(Match match, Result result) {

    }

    public void match(Match match) {

    }

    public void embedding(String s, byte[] bytes) {

    }

    public void write(String s) {

    }
}
