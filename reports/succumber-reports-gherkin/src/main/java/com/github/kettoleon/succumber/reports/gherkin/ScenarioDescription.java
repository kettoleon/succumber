package com.github.kettoleon.succumber.reports.gherkin;

import gherkin.formatter.model.*;

import java.util.LinkedList;
import java.util.List;

public class ScenarioDescription {
    private final Scenario scenario;
    private Background background;
    private List<StepExecution> backgroundSteps = new LinkedList<>();
    private List<StepExecution> scenarioSteps = new LinkedList<>();

    public ScenarioDescription(Scenario scenario) {
        this.scenario = scenario;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public Background getBackground() {
        return background;
    }

    public void addBackgroundStep(StepExecution step) {
        backgroundSteps.add(step);
    }

    public void addScenarioStep(StepExecution step) {
        scenarioSteps.add(step);
    }

    public Scenario getScenario() {
        return scenario;
    }

    public List<StepExecution> getBackgroundSteps() {
        return backgroundSteps;
    }

    public List<StepExecution> getScenarioSteps() {
        return scenarioSteps;
    }
}
