package com.aws.s3.load;

import java.io.File;

import org.apache.log4j.Logger;

import com.amazonaws.services.s3.model.PutObjectRequest;

class AwsS3FileUploader implements AwsS3FileLoader {

    private static final Logger LOGGER = Logger.getLogger(AwsS3FileUploader.class);

    private final AwsS3Client s3Client;

    private final String bucketName;

    AwsS3FileUploader(final AwsS3Client s3Client, final String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public void load(final String fileNameToUpload, final String fileWithAbsolutePathToUpload) {
        try {
            LOGGER.info("Upload starting...");
            s3Client.upload(new PutObjectRequest(bucketName, fileNameToUpload, new File(fileWithAbsolutePathToUpload)));
            LOGGER.info("Upload complete.");
        } catch (Exception e) {
            throw new FileLoaderException("Error occurred while uploading file", e);
        }
    }
}
