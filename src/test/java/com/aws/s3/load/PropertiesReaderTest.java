package com.aws.s3.load;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PropertiesReaderTest {

    @Test
    public void testLoad_withValidPropertiesFile() {
        PropertiesReader testReader = new PropertiesReader(
                getClass().getClassLoader().getResource("test.properties").getPath());
        assertEquals("testValue_1", testReader.get("TEST_KEY_1"));
        assertEquals("testValue_2", testReader.get("TEST_KEY_2"));
        // assertEquals("dummy_access_key_id",
        // testReader.get("AWS_ACCESS_KEY_ID"));
        // assertEquals("dummy_secret_access_key",
        // testReader.get("AWS_SECRET_ACCESS_KEY"));
        // assertEquals("us-west-1", testReader.get("AWS_REGION"));

    }

    @Test
    public void testLoad_withInvalidPropertiesFile() {
        try {
            new PropertiesReader("test.properties");
        } catch (Exception e) {
            assertTrue(e instanceof FileLoaderException);
        }
    }

}
