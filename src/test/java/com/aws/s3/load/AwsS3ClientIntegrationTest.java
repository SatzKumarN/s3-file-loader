package com.aws.s3.load;

import static java.nio.file.Files.readAllBytes;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import io.findify.s3mock.S3Mock;

public class AwsS3ClientIntegrationTest {

    private S3Mock api;

    private AmazonS3 amazonS3;

    private AwsS3Client testS3Client;

    @Before
    public void setUp() {
        api = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
        api.start();
        final EndpointConfiguration endpoint = new EndpointConfiguration("http://localhost:8001", "us-west-2");
        amazonS3 = AmazonS3ClientBuilder.standard().withPathStyleAccessEnabled(true).withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials())).build();
        amazonS3.createBucket("testbucket");
        testS3Client = new AwsS3Client(amazonS3, 5);
    }

    @After
    public void tearDown() {
        testS3Client.close();
        api.shutdown();
    }

    @Test
    public void testFileUploadDownload_validateFile() throws Exception {
        final File uploadedFile = new File(getClass().getClassLoader().getResource("dummy_file.txt").getPath());
        testS3Client.upload(new PutObjectRequest("testbucket", "file_in_s3.txt", uploadedFile));
        testS3Client.download(new GetObjectRequest("testbucket", "file_in_s3.txt"), "downloaded_file.txt");
        final File downloadedFile = new File("downloaded_file.txt");
        assertArrayEquals(readAllBytes(uploadedFile.toPath()), readAllBytes(downloadedFile.toPath()));
    }
}
