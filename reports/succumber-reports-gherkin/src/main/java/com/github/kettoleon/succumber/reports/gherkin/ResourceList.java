package com.github.kettoleon.succumber.reports.gherkin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * list resources available from the classpath @ *
 */
public class ResourceList{

    /**
     * for all elements of java.class.path get a Collection of resources Pattern
     * pattern = Pattern.compile(".*"); gets all resources
     * 
     * @param pattern
     *            the pattern to match
     * @return the resources in the order they are found
     */
    public static Map<String, InputStream> getResources(
        final Pattern pattern) throws IOException {
        final Map<String, InputStream> retval = new HashMap<>();
        final String classPath = System.getProperty("java.class.path", ".");
        final String[] classPathElements = classPath.split(System.getProperty("path.separator"));
        for(final String element : classPathElements){
            retval.putAll(getResources(element, pattern));
        }
        return retval;
    }

    private static Map<String, InputStream> getResources(
        final String element,
        final Pattern pattern) throws IOException {
        final Map<String, InputStream> retval = new HashMap<>();
        final File file = new File(element);
        if(file.isDirectory()){
            retval.putAll(getResourcesFromDirectory(file, pattern));
        } else if(file.exists() && file.getName().endsWith("jar")){
            if(file.getName().contains("succumber")){
                System.out.println("Succumber jar file: "+file.getName());
            }
            retval.putAll(getResourcesFromJarFile(file, pattern));
        }
        return retval;
    }

    private static Map<String, InputStream> getResourcesFromJarFile(
        final File file,
        final Pattern pattern) throws IOException {
        final HashMap<String, InputStream> retval = new HashMap<>();
        ZipFile zf;
        try{
            zf = new ZipFile(file);
        } catch(final ZipException e){
            throw new Error(e);
        } catch(final IOException e){
            throw new Error(e);
        }
        final Enumeration e = zf.entries();
        while(e.hasMoreElements()){
            final ZipEntry ze = (ZipEntry) e.nextElement();
            final String fileName = ze.getName();
            final boolean accept = pattern.matcher(fileName).matches();
            if(accept){
                retval.put(fileName, zf.getInputStream(ze));
            }
        }
        try{
            if(retval.isEmpty()) {
                zf.close(); //TODO need to better handle this, but works! :)
            }
        } catch(final IOException e1){
            throw new Error(e1);
        }
        return retval;
    }

    private static Map<String, InputStream> getResourcesFromDirectory(
        final File directory,
        final Pattern pattern){
        final Map<String, InputStream> retval = new HashMap<>();
        final File[] fileList = directory.listFiles();
        for(final File file : fileList){
            if(file.isDirectory()){
                retval.putAll(getResourcesFromDirectory(file, pattern));
            } else{
                try{
                    final String fileName = file.getCanonicalPath();
                    final boolean accept = pattern.matcher(fileName).matches();
                    if(accept){
                        retval.put(fileName, new FileInputStream(fileName));
                    }
                } catch(final IOException e){
                    throw new Error(e);
                }
            }
        }
        return retval;
    }

    /**
     * list the resources that match args[0]
     * 
     * @param args
     *            args[0] is the pattern to match, or list all resources if
     *            there are no args
     */
    public static void main(final String[] args) throws IOException {
        Pattern pattern;
        if(args.length < 1){
            pattern = Pattern.compile(".*");
        } else{
            pattern = Pattern.compile(args[0]);
        }
        final Map<String, InputStream> list = ResourceList.getResources(pattern);
        for(final String name : list.keySet()){
            System.out.println(name);
        }
    }
}  