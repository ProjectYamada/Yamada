package com.yamada.notkayla.module;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class Adapter {
    private Class<?> attachedClass;
    private String classPath;
    private Map<String,Method> methods = new HashMap<>();
    private static SanaeClassLoader cl = new SanaeClassLoader();
    private Object module;

    protected Adapter(String classPath,Map config) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        attachedClass = cl.loadClass(classPath);
        this.classPath = classPath;
        module = attachedClass.getDeclaredConstructor(Map.class).newInstance(config);
        for(Method m : attachedClass.getMethods()) methods.put(m.getName(),m);
    }

    protected Object runMethod(String name, Object... args) throws InvocationTargetException, IllegalAccessException {
        return methods.get(name).invoke(module,args);
    }

    public void reload() throws IllegalAccessException, InstantiationException {
        attachedClass = null;
        module = null;
        attachedClass = cl.loadClass(classPath);
        module = attachedClass.newInstance();
    }
}
