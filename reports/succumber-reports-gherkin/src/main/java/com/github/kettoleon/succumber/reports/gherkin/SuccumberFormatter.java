package com.github.kettoleon.succumber.reports.gherkin;

import com.google.gson.Gson;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuccumberFormatter implements Formatter, Reporter {

    private static final Logger log = LoggerFactory.getLogger(SuccumberFormatter.class);

    private List<Feature> features = new ArrayList<>();

    public SuccumberFormatter() {
        log.trace("{} instantiated", SuccumberFormatter.class.getSimpleName());
        //Load DI context and configuration
    }

    public void syntaxError(String s, String s1, List<String> list, String s2, Integer integer) {

    }

    public void uri(String s) {

    }

    public void feature(Feature feature) {
        features.add(feature);
    }

    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    public void examples(Examples examples) {

    }

    public void startOfScenarioLifeCycle(Scenario scenario) {

    }

    public void background(Background background) {

    }

    public void scenario(Scenario scenario) {

    }

    public void step(Step step) {

    }

    public void endOfScenarioLifeCycle(Scenario scenario) {

    }

    public void done() {

    }

    public void close() {
        try {
        String targetFolder = "target/reports/succumber";
        File featuresAssetFile = new File(targetFolder+"/assets/features.jsonp");
        featuresAssetFile.getParentFile().mkdirs();
        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter(featuresAssetFile);
        fileWriter.append("declareAsset({\n" +
                "    \"asset\": \"features\",\n" +
                "    \"data\":");
        gson.toJson(features, fileWriter);
        fileWriter.append("});");
        fileWriter.close();

        //Close streams
        //Extract succumber-reports-ui folder
        String resourcesFolder = "succumber-reports-ui";
        InputStream folder = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcesFolder);


            List<String> files = Arrays.asList(IOUtils.toString(folder, "UTF-8").split("\\n"));
            for (String file : files) {
                if (file.matches(".*\\..*")) {
                    System.out.println("Unpacking: " + file);
                    unpack(resourcesFolder + "/" + file, targetFolder + "/" + file);
                } else {
                    System.out.println("Skipping:  " + file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void unpack(String resource, String targetFile) throws IOException {
        FileUtils.copyInputStreamToFile(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource), new File(targetFile));
    }

    public void eof() {

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
