package com.aws.s3.load;

import static com.amazonaws.regions.Regions.fromName;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

class AmazonS3Factory {

    private final PropertiesReader reader;

    AmazonS3Factory(final PropertiesReader reader) {
        this.reader = reader;
    }

    public AmazonS3 getInstance() {
        final String accessKey = reader.get("AWS_ACCESS_KEY_ID");
        final String secretKey = reader.get("AWS_SECRET_ACCESS_KEY");
        final String region = reader.get("AWS_REGION");
        final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(fromName(region)).build();
    }
}
