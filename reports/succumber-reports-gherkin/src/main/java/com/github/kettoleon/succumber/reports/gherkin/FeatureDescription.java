package com.github.kettoleon.succumber.reports.gherkin;

import gherkin.formatter.model.Feature;

import java.util.LinkedList;
import java.util.List;

public class FeatureDescription {


    private final String uri;
    private Feature feature;

    private List<ScenarioDescription> scenarios = new LinkedList<>();

    public FeatureDescription(String uri) {
        this.uri = uri;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public void addScenario(ScenarioDescription currentScenario) {
        scenarios.add(currentScenario);
    }

    public List<ScenarioDescription> getScenarios() {
        return scenarios;
    }
}
