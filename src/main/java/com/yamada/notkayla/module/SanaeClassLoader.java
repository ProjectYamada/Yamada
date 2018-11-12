package com.yamada.notkayla.module;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SanaeClassLoader extends ClassLoader {
    private static Logger log = Logger.getLogger("SanaeLoader");
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        log.log(Level.INFO,name);
        Class<?> ie;
        return (ie = super.loadClass(name)) == null ? findClass(name) : ie;
    }

    @Override
    public Class<?> findClass(String s) {
        try {
            if (s.contentEquals("com.yamada.notkayla.Yamada")) throw new IOException("this is bad practice but shut up lol");
            byte[] bytes = loadClassData(s);
            return defineClass(s, bytes, 0, bytes.length);
        } catch (IOException ioe) {
            try {
                return super.loadClass(s);
            } catch (ClassNotFoundException ignore) { }
            ioe.printStackTrace();
            return null;
        }
    }

    private byte[] loadClassData(String className) throws IOException {
        File f = new File("./build/classes/java/main/" + className.replaceAll("\\.", "/") + ".class");
        int size = (int) f.length();
        byte buff[] = new byte[size];
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        dis.readFully(buff);
        dis.close();
        return buff;
    }
}
