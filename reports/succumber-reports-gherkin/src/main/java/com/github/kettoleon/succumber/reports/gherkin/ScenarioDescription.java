package com.github.kettoleon.succumber.reports.gherkin;

import gherkin.formatter.model.Background;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.Step;

import java.util.LinkedList;
import java.util.List;

public class ScenarioDescription {
    private final Scenario scenario;
    private Background background;
    private List<Step> backgroundSteps = new LinkedList<>();
    private List<Step> scenarioSteps = new LinkedList<>();

    public ScenarioDescription(Scenario scenario) {
        this.scenario = scenario;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public Background getBackground() {
        return background;
    }

    public void addBackgroundStep(Step step) {
        backgroundSteps.add(step);
    }

    public void addScenarioStep(Step step) {
        scenarioSteps.add(step);
    }

    public Scenario getScenario() {
        return scenario;
    }

    public List<Step> getBackgroundSteps() {
        return backgroundSteps;
    }

    public List<Step> getScenarioSteps() {
        return scenarioSteps;
    }
}
