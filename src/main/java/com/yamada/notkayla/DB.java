package com.yamada.notkayla;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/** WIP */
@SuppressWarnings("DanglingJavadoc")
public class DB {
    private static HashMap<String, Object> map;
    private static File file; //the file to read and write settings to

    /** VARIABLES TO KEEP TRACK OF */

    /* Classes here must implement Serializable, and should declare serialVersionUIDs
       See https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
       https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/Serializable.html
       https://stackoverflow.com/questions/888335/why-generate-long-serialversionuid-instead-of-a-simple-1l (answer 2)
     */

    public static HashMap<String, Integer> testHashmap;
    public static HashMap<String, String> testHashmap2;

    /** VARIABLES TO KEEP TRACK OF */

    //call this at the beginning of the program
    public static void init() {
        file = new File("database.obj");

        if (file.exists()) {
            load();
        } else {
            makeDefaults();
        }
    }

    private DB() {} //singleton

    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> deserialize(File file) {
        HashMap<String, Object> map = null;

        try {
            FileInputStream streamIn = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(streamIn);

            map = (HashMap<String, Object>) ois.readObject();

            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace(); //shouldn't happen
        }

        return map;
    }

    public static <T> Object instantiate(Class<T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace(); return null;
        }
    }

    private static void load() {
        map = deserialize(file);

        //load map into fields
        for (Field f : DB.class.getFields()) {
            //f.setAccessible(true);

            try {
                if (f.get(null) == null) {

                    String name = f.getName();

                    if (map.containsKey(name)) {
                        f.set(null, map.get(f.getName()));
                    } else {
                        Object instance = instantiate(f.getType());

                        f.set(null, instance);
                        map.put(f.getName(), instance);
                    }
                }
            } catch (IllegalAccessException iae) {
                iae.printStackTrace(); //shouldn't happen
            }
        }
    }

    private static void makeDefaults() {
        //add hardcoded field defaults here

        //instantiate empty versions for null fields
        for (Field f : DB.class.getFields()) {
            //f.setAccessible(true);

            try {
                if (f.get(null) == null) {
                    Object instance = instantiate(f.getType());

                    f.set(null, instance);
                    map.put(f.getName(), instance);
                }
            } catch (IllegalAccessException iae) {
                iae.printStackTrace(); //shouldn't happen
            }
        }
    }
}

