package com.aws.s3.load;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AwsS3FileUploaderTest {

    @Mock
    private AwsS3Client s3Client;

    @Before
    public void setUp() {
        openMocks(this);
    }

    private void doUpload() {
        final AwsS3FileUploader fileUploader = new AwsS3FileUploader(s3Client, "test_bucket");
        fileUploader.load("test_file.txt", "/home/test_file.txt");
    }

    @Test
    public void testUpload_success() throws AmazonClientException, InterruptedException {
        doNothing().when(s3Client).upload(any());

        doUpload();

        final ArgumentCaptor<PutObjectRequest> putRequestCaptor = forClass(PutObjectRequest.class);
        verify(s3Client).upload(putRequestCaptor.capture());
        final PutObjectRequest putRequest = putRequestCaptor.getValue();
        assertEquals("test_file.txt", putRequest.getKey());
        assertEquals("test_bucket", putRequest.getBucketName());
        assertEquals(new File("/home/test_file.txt"), putRequest.getFile());
    }

    @Test
    public void testUpload_failure() throws AmazonClientException, InterruptedException {
        doThrow(AmazonClientException.class).when(s3Client).upload(any());
        try {
            doUpload();
        } catch (Exception e) {
            assertTrue(e instanceof FileLoaderException);
            assertEquals("Error occurred while uploading file", e.getMessage());
        }
    }

}
