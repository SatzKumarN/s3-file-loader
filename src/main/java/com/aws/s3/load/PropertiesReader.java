package com.aws.s3.load;

import java.io.FileReader;
import java.util.Properties;

import org.apache.log4j.Logger;

class PropertiesReader {

    private static final Logger LOGGER = Logger.getLogger(PropertiesReader.class);

    private final Properties properties;

    PropertiesReader(final String filePath) {
        properties = load(filePath);
    }

    private Properties load(final String awsPropertiesFile) {
        try (FileReader fileReader = new FileReader(awsPropertiesFile)) {
            Properties props = new Properties();
            props.load(fileReader);
            return props;
        } catch (Exception e) {
            LOGGER.error("Unable to load the given properties", e);
            throw new FileLoaderException("Unable to load the given properties", e);
        }
    }

    public String get(final String key) {
        return properties.getProperty(key);
    }
}
