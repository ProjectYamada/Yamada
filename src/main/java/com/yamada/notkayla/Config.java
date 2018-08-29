package com.yamada.notkayla;

import com.yamada.notkayla.commands.Checks;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Config {
    private static Yaml yaml = new Yaml();
    public static Map configuration;

    static void init() {
        try{
            Path curdir = Paths.get(System.getProperty("user.dir"));
            Path config = Paths.get(curdir.toString(),"config.yml");
            configuration = (Map) yaml.load(new FileInputStream(config.toFile()));
            Checks.owners = (List) configuration.get("owners");
        } catch (FileNotFoundException e) {
            Kayla.log.log(Level.SEVERE, "FileNotFoundError: file 'config.yml' does not exist");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
