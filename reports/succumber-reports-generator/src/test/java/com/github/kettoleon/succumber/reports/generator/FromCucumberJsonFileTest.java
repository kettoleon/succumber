package com.github.kettoleon.succumber.reports.generator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FromCucumberJsonFileTest {

    private String bigJsonFile = "/home/enric/workspaces/transComms/cbs-transactional-comms-cucumber-report-generator/src/test/resources/results.json";

    @Test
    public void readPerformance_entireTree_takes12Seconds() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.reader().readTree(new FileReader(bigJsonFile));
    }

        @Test
    public void readPerformance_withJsonParser_takes7Seconds() throws IOException {

        JsonFactory jsonFactory = new MappingJsonFactory();
        JsonParser jp = jsonFactory.createParser(new File(bigJsonFile));

        JsonToken current;
        current = jp.nextToken();
        if (current != JsonToken.START_ARRAY) {
            System.out.println("Error: root should be array: quiting.");
            return;
        }
        while (jp.nextToken() != JsonToken.END_ARRAY) {
            // read the record into a tree model,
            // this moves the parsing position to the end of it
            JsonNode node = jp.readValueAsTree();
            // And now we have random access to everything in the object
            System.out.println("id: " + node.get("id").asText());
            System.out.println("name: " + node.get("name").asText());
        }
    }


}
