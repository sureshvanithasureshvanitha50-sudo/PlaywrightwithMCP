package com.demo.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestData {
    private final Properties properties = new Properties();

    public TestData(String fileName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IllegalArgumentException("Unable to find test data file: " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data file: " + fileName, e);
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
