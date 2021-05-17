package com.aws.s3.load;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.model.GetObjectRequest;

public class AwsS3FileDownloaderTest {

    @Mock
    private AwsS3Client s3Client;

    @Before
    public void setUp() {
        openMocks(this);
    }

    private void doDownload() {
        final AwsS3FileDownloader fileDownloader = new AwsS3FileDownloader(s3Client, "test_bucket");
        fileDownloader.load("test_file.txt", "/home/test_file.txt");
    }

    @Test
    public void testDownload_success() throws AmazonClientException, InterruptedException {
        doNothing().when(s3Client).download(any(), anyString());

        doDownload();

        final ArgumentCaptor<GetObjectRequest> getRequestCaptor = forClass(GetObjectRequest.class);
        verify(s3Client).download(getRequestCaptor.capture(), eq("/home/test_file.txt"));
        assertEquals(new GetObjectRequest("test_bucket", "test_file.txt"), getRequestCaptor.getValue());
    }

    @Test
    public void testDownload_failure() throws AmazonClientException, InterruptedException {
        doThrow(AmazonClientException.class).when(s3Client).download(any(), anyString());
        try {
            doDownload();
        } catch (Exception e) {
            assertTrue(e instanceof FileLoaderException);
            assertEquals("Error occurred while downloading file", e.getMessage());
        }
    }

}
