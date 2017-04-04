# succumber-reports-generator
This module is meant to be a common library for all the reports generators.

Initially we will make a maven plugin and a jenkins plugin, both depending on this project.
Also the gherkin formatter will need it if the user wants to generate reports from there.

This module should include functionality to read from huge cucumber result.json files.