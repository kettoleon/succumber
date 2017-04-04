package com.github.kettoleon.succumber.model.configuration;

import java.util.LinkedHashSet;

public class Configuration {

    // A list of children modules in the same project
    private LinkedHashSet<Module> modules;

    public LinkedHashSet<Module> getModules() {
        return modules;
    }

    public void setModules(LinkedHashSet<Module> modules) {
        this.modules = modules;
    }
}
