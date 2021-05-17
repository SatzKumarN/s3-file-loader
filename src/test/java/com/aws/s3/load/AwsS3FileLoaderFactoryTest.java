package com.aws.s3.load;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class AwsS3FileLoaderFactoryTest {

    @Mock
    private AwsS3Client s3Client;

    @Before
    public void setUp() {
        openMocks(this);
    }

    private void assertInstance(final String loadFlag, final Class<? extends AwsS3FileLoader> expected) {
        final AwsS3FileLoaderFactory factory = new AwsS3FileLoaderFactory(s3Client, "dummy_bucket");
        final AwsS3FileLoader loader = factory.getInstance(loadFlag);
        assertEquals(expected, loader.getClass());
    }

    @Test
    public void testGetInstance_download() {
        assertInstance("download", AwsS3FileDownloader.class);
    }

    @Test
    public void testGetInstance_upload() {
        assertInstance("upload", AwsS3FileUploader.class);
    }

    @Test
    public void testGetInstance_otherThanDownloadOrUpload() {
        try {
            final AwsS3FileLoaderFactory factory = new AwsS3FileLoaderFactory(s3Client, "dummy_bucket");
            factory.getInstance("random");
        } catch (Exception e) {
            assertTrue(e instanceof FileLoaderException);
            assertEquals("Unsupported operation", e.getMessage());
        }
    }

}
