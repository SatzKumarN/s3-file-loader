package com.aws.s3.load;

import static com.amazonaws.services.s3.model.Region.US_West;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.amazonaws.services.s3.AmazonS3;

public class AmazonS3FactoryTest {

    @Mock
    private PropertiesReader reader;

    @Before
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testGetInstance_success() {
        doReturn("dummy_access_key_id").when(reader).get("AWS_ACCESS_KEY_ID");
        doReturn("dummy_secret_access_key").when(reader).get("AWS_SECRET_ACCESS_KEY");
        doReturn("us-west-1").when(reader).get("AWS_REGION");
        final AmazonS3Factory factory = new AmazonS3Factory(reader);
        final AmazonS3 s3 = factory.getInstance();
        assertEquals(US_West, s3.getRegion());
    }

}
