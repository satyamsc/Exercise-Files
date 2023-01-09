package com.springbank.bankacc.core.configuration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.SunUnsafeReflectionProvider;
import com.thoughtworks.xstream.core.util.CompositeClassLoader;
import com.thoughtworks.xstream.mapper.CachingMapper;
import org.junit.jupiter.api.Test;

class AxonConfigTest {
    @Test
    void testXStream() {
        XStream actualXStreamResult = (new AxonConfig()).xStream();
        ClassLoader classLoader = actualXStreamResult.getClassLoader();
        assertTrue(classLoader instanceof CompositeClassLoader);
        assertTrue(actualXStreamResult.getReflectionProvider() instanceof SunUnsafeReflectionProvider);
        assertTrue(actualXStreamResult.getMapper() instanceof CachingMapper);
        assertSame(classLoader, actualXStreamResult.getClassLoaderReference().getReference());
    }
}

