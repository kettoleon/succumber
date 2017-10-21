package com.github.kettoleon.succumber.reports.gherkin.cucumber.steps;

import cucumber.api.java.en.Given;

public class SimpleSteps {

    @Given("I have (\\d+) cukes in my belly")
    public void I_have_cukes_in_my_belly(int cukes) {
        System.out.format("Cukes: %d\n", cukes);
    }

}
