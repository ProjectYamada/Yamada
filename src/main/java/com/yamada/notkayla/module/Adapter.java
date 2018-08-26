package com.yamada.notkayla.module;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class Adapter {
    private Class<?> attachedClass;
    Reflections r = new Reflections();
    Map<String,Method> methods = new HashMap<>();
    static SanaeClassLoader cl = new SanaeClassLoader();
    Object module;

    public Adapter(Class<?> clazz) {
        attachedClass = clazz;
        for(Method m : attachedClass.getMethods()) methods.put(m.getName(),m);
    }

    Object runMethod(String name, Object... args) {
        return methods;
    }
}
