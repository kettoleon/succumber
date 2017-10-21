package com.github.kettoleon.succumber.reports.gherkin;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.kettoleon.succumber.configuration.model.Configuration;
import com.github.kettoleon.succumber.configuration.model.Module;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SuccumberFormatter implements Formatter, Reporter {

    private static final Logger log = LoggerFactory.getLogger(SuccumberFormatter.class);
    private final Module currentModule;
    private File currentTargetFolder;
    private Configuration configuration;

    private List<Feature> features = new ArrayList<>();
    private FeatureDescription currentFeature;
    private ScenarioDescription currentScenario;
    private boolean currentlyInBackground = false;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Match currentMatch;
    private Step currentStep;

    public SuccumberFormatter() {
        log.trace("{} instantiated", SuccumberFormatter.class.getSimpleName());
        //Load DI context and configuration

        //Configuration loader should be common and reusable.
        //It should parse some of the fields and even validate them.
        //We should select here the current module
        try {
            configuration = objectMapper.readValue(new FileReader("src/test/resources/succumber.json"), Configuration.class);
            currentModule = configuration.getModules().iterator().next();
            currentTargetFolder = new File(new File("src/test/resources/succumber.json").getAbsoluteFile().getParentFile(), currentModule.getReportsTargetPath());
            currentTargetFolder = currentTargetFolder.toPath().normalize().toFile();
            log.info("targetFolder: " + currentTargetFolder.getAbsoluteFile().getPath());

            // Clear previous assets if they exist
            FileUtils.deleteQuietly(currentTargetFolder);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load succumber configuration");
        }
    }

    public void syntaxError(String s, String s1, List<String> list, String s2, Integer integer) {
        //TODO handle syntax errors
    }

    public void uri(String uri) {
        currentFeature = new FeatureDescription(uri);
        currentlyInBackground = false;
    }

    public void feature(Feature feature) {
        currentFeature.setFeature(feature);
        features.add(feature);
    }

    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        //It would seem we don't need to keep track of these for reporting purposes
    }

    public void examples(Examples examples) {
        //It would seem we don't need to keep track of these for reporting purposes
    }

    public void startOfScenarioLifeCycle(Scenario scenario) {
        currentScenario = new ScenarioDescription(scenario);
        currentFeature.addScenario(currentScenario);
    }

    public void background(Background background) {
        currentScenario.setBackground(background);
        currentlyInBackground = true;
    }

    public void scenario(Scenario scenario) {
        currentlyInBackground = false;
    }

    public void step(Step step) {
       currentStep = step;
    }

    public void endOfScenarioLifeCycle(Scenario scenario) {

        currentlyInBackground = false;
    }

    public void done() {

    }

    public void eof() {
        try {
            saveAsset(new Asset("features/" + currentFeature.getFeature().getId() + "/definition", currentFeature));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            saveAsset(new Asset<>("features", features));

            //Close streams
            //Extract succumber-reports-ui folder
            new ResourceFolderExtractor("succumber-reports-ui", currentTargetFolder).extract();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void saveAsset(Asset asset) throws IOException {
        File featuresAssetFile = new File(currentTargetFolder, "/assets/" + asset.getAsset() + ".jsonp");
        featuresAssetFile.getParentFile().mkdirs();
        FileWriter fileWriter = new FileWriter(featuresAssetFile);
        fileWriter.append("declareAsset(");
        ObjectWriter jsonWriter = objectMapper.writerFor(Asset.class).without(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
        jsonWriter.writeValue(fileWriter, asset);
        fileWriter.append(");");
        fileWriter.close();
    }

    /* Reporter methods */

    public void before(Match match, Result result) {
        System.out.format("before: %s %s\n", match.getLocation(), result.getStatus());
    }

    public void result(Result result) {
        System.out.format("result: %s\n", result.getStatus());
        if (currentlyInBackground) {
            currentScenario.addBackgroundStep(new StepExecution(currentStep,currentMatch,result));
        } else {
            currentScenario.addScenarioStep(new StepExecution(currentStep,currentMatch,result));
        }
    }

    public void after(Match match, Result result) {
        System.out.format("after: %s %s\n", match.getLocation(), result.getStatus());
    }

    public void match(Match match) {
        System.out.format("match: %s\n", match.getLocation());
        currentMatch = match;
    }

    public void embedding(String s, byte[] bytes) {
        System.out.format("embedding: %s\n", s);
    }

    public void write(String s) {
        System.out.format("write: %s\n", s);
    }
}
