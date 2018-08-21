package com.yamada.notkayla;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;

public class Config {
    private static Yaml yaml = new Yaml();
    public static Map<String, Object> configuration;

    static void init(File config) {
        try{
            configuration = (Map) yaml.load(new FileInputStream(config));
        } catch (FileNotFoundException e) {
            Kayla.log.log(Level.SEVERE, "FileNotFoundError: file 'config.yml' does not exist");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
