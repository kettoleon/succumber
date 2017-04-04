package com.github.kettoleon.succumber.reports.gherkin;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.kettoleon.succumber.configuration.model.Configuration;
import com.github.kettoleon.succumber.configuration.model.Module;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
            FileUtils.deleteQuietly(currentTargetFolder);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load succumber configuration");
        }
        // Clear previous assets if they exist

    }

    public void syntaxError(String s, String s1, List<String> list, String s2, Integer integer) {

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

    }

    public void examples(Examples examples) {

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
        if (currentlyInBackground) {
            currentScenario.addBackgroundStep(step);
        } else {
            currentScenario.addScenarioStep(step);
        }
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
            extractUI();
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
        jsonWriter.writeValue(fileWriter,asset);
//        objectMapper.writeValue(fileWriter, asset);
        fileWriter.append(");");
        fileWriter.close();
    }

    private void extractUI() throws IOException {
        String resourcesFolder = "succumber-reports-ui";
        InputStream folder = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcesFolder);


        List<String> files = Arrays.asList(IOUtils.toString(folder, "UTF-8").split("\\n"));
        for (String file : files) {
            if (file.matches(".*\\..*")) {
//                System.out.println("Unpacking: " + file);
                unpack(resourcesFolder + "/" + file, new File(currentTargetFolder, file));
            } else {
//                System.out.println("Skipping:  " + file);
            }
        }
    }

    private void unpack(String resource, File targetFile) throws IOException {
        FileUtils.copyInputStreamToFile(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource), targetFile);
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
