package com.yamada.notkayla.module;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SanaeClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name) {
        return findClass(name);
    }

    @Override
    public Class<?> findClass(String s) {
        try {
            byte[] bytes = loadClassData(s);
            return defineClass(s, bytes, 0, bytes.length);
        } catch (IOException ioe) {
            try {
                return super.loadClass(s);
            } catch (ClassNotFoundException ignore) { }
            ioe.printStackTrace(System.out);
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
