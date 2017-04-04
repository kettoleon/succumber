package com.github.kettoleon.succumber.model.configuration;

import java.util.LinkedHashSet;
import java.util.List;

public class Module {

    // Used for creating folders, so we should validate it.
    private String id;

    // Used to display it in the reports
    private String title;

    // Shown in the reports right after the title and before the list of features
    private String description;

    // In case the user wants to choose which features belong to each module
    // We can also read .md files in there to group features by sections
    private List<String> features;

    // If we have multiple runners, for example, one per different environment, and we want to put them together.
    private List<Runner> runners;

    // A list of children modules in the same project
    private LinkedHashSet<Module> modules;

    // A list of children modules that point to a different project
    private List<String> includedModules;

    // Where to publish the reports and/or the assets
    private String reportsTargetPath;

    // In case the user just wants to generate the reports from supported cucumber results.json
    private String cucumberResultsFile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public LinkedHashSet<Module> getModules() {
        return modules;
    }

    public void setModules(LinkedHashSet<Module> modules) {
        this.modules = modules;
    }

    public List<String> getIncludedModules() {
        return includedModules;
    }

    public void setIncludedModules(List<String> includedModules) {
        this.includedModules = includedModules;
    }

    public String getReportsTargetPath() {
        return reportsTargetPath;
    }

    public void setReportsTargetPath(String reportsTargetPath) {
        this.reportsTargetPath = reportsTargetPath;
    }

    public String getCucumberResultsFile() {
        return cucumberResultsFile;
    }

    public void setCucumberResultsFile(String cucumberResultsFile) {
        this.cucumberResultsFile = cucumberResultsFile;
    }

    public List<Runner> getRunners() {
        return runners;
    }

    public void setRunners(List<Runner> runners) {
        this.runners = runners;
    }
}
