package com.github.kettoleon.succumber.reports.gherkin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.regex.Pattern.compile;

public class ResourceFolderExtractor {

    private final String path;
    private final File to;

    public ResourceFolderExtractor(String path, File to) {
        this.path = path;
        this.to = to;
    }


    public void extract() throws IOException {
        Map<String, InputStream> files = obtainFilesToExtract();

        for (Map.Entry<String, InputStream> file : files.entrySet()) {
            unpack(file.getValue(), new File(to, file.getKey()));
        }


    }

    private void unpack(InputStream from, File to) throws IOException {
        FileUtils.copyInputStreamToFile(from, to);
    }

    private Map<String, InputStream> obtainFilesToExtract() throws IOException {
        Map<String, InputStream> files = new HashMap<>();

        InputStream root = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

        obtainFilesToExtractViaClasspath(files, root);

        if (files.isEmpty()) {
            obtainFilesToExtractViaJarFile(files);
        }

        if (files.isEmpty()) {
            throw new RuntimeException("Unable to extract resource files");
        }

        return files;

    }

    private void obtainFilesToExtractViaJarFile(Map<String, InputStream> files) throws IOException {
        Map<String, InputStream> list = ResourceList.getResources(compile(path + ".*"));

        for (Map.Entry<String, InputStream> entry : list.entrySet()) {
            String file = entry.getKey().substring(path.length() + 1);

            if (file.matches(".*\\..*") && !file.contains("\\")) {
                System.out.println("Unpacking: " + file);
                files.put(file, entry.getValue());
            } else {
                System.out.println("Skipping:  " + file);
            }

        }

//        throw new RuntimeException("Not yet supported");
    }

    private void obtainFilesToExtractViaClasspath(Map<String, InputStream> files, InputStream root) throws IOException {
        try {
            List<String> filesInDir = Arrays.asList(IOUtils.toString(root, "UTF-8").split("\\n"));
            for (String file : filesInDir) {
                if (file.matches(".*\\..*")) {
                    System.out.println("Unpacking: " + file);
                    files.put(file, Thread.currentThread().getContextClassLoader().getResourceAsStream(path + "/" + file));
                } else {
                    System.out.println("Skipping:  " + file);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
