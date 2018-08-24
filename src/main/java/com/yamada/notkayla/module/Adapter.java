package com.yamada.notkayla.module;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class Adapter {
    private Class<?> attachedClass;
    Map<String,Method> map = new HashMap<>();
    Reflections r = new Reflections();
    protected static SanaeClassLoader cl = new SanaeClassLoader();

    public Adapter(Class<?> clazz){
        attachedClass = clazz;
        ReflectionUtils.getMethods(clazz);
    }
    public Object runMethod(String method,String[] args) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
