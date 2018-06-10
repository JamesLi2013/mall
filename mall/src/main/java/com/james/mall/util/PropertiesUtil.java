package com.james.mall.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesUtil {
    public static final String ROOT_DIR="d:/opt/hello/config";

    public static void store(String fileName, String key, String value){
        Properties properties = new Properties();
        properties.setProperty(key,value);
        store(fileName,properties);
    }

    public static void store(String fileName, Properties properties){
        FileOutputStream fos = null;

        try {
            if(!new File(ROOT_DIR).exists()) Files.createDirectories(Paths.get(ROOT_DIR));
            File file = new File(ROOT_DIR, fileName + ".config");
            fos = new FileOutputStream(file);
            properties.store(fos,"properties");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String get(String fileName, String key){
        Properties properties = get(fileName);
        return properties.getProperty(key);
    }

    public static Properties get(String fileName){
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(ROOT_DIR,fileName+".config"));
            properties.load(fis);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            return properties;
        }

        return properties;
    }
}
