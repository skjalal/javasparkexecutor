package com.example.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SparkConstantTest {

    @Test
    void testSparkConstant() throws NoSuchMethodException {
        Constructor<SparkConstant> constructor = SparkConstant.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            try {
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw (UnsupportedOperationException) e.getTargetException();
            }
        });
    }
}