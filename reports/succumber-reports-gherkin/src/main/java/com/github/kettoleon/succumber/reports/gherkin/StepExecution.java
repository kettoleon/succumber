package com.github.kettoleon.succumber.reports.gherkin;

import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Step;

public class StepExecution {
    private Step stepDefinition;
    private Match match;
    private Result result;

    public StepExecution(Step stepDefinition, Match currentMatch, Result result) {
        this.stepDefinition = stepDefinition;
        this.match = currentMatch;
        this.result = result;
    }

    public Step getStepDefinition() {
        return stepDefinition;
    }

    public void setStepDefinition(Step stepDefinition) {
        this.stepDefinition = stepDefinition;
    }

    public Match getMatch() {
        return match;
    }

    public Result getResult() {
        return result;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
