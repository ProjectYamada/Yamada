package com.yamada.notkayla;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;

public class Config {
    private static Yaml yaml = new Yaml(new Constructor(ConfigSchema.class));

    public static void init(File config) {
        try{
            yaml.load(new FileInputStream(config));
        } catch (FileNotFoundException e) {
            Kayla.log.log(Level.SEVERE, "FileNotFoundError: file 'config.yml' does not exist");
            e.printStackTrace();
        }
    }

    class ConfigSchema{
        private String token;
        private Map<String,String> db;//todo: database login thingy

        public String getToken() {
            return token;
        }

        public Map<String, String> getDb() {
            return db;
        }
    }
}
