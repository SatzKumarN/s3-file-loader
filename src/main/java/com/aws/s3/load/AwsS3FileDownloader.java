package com.aws.s3.load;

import org.apache.log4j.Logger;

import com.amazonaws.services.s3.model.GetObjectRequest;

class AwsS3FileDownloader implements AwsS3FileLoader {

    private static final Logger LOGGER = Logger.getLogger(AwsS3FileDownloader.class);

    private final AwsS3Client s3Client;

    private final String bucketName;

    AwsS3FileDownloader(final AwsS3Client s3Client, final String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public void load(final String fileNameToLoad, final String fileWithAbsolutePathToLoad) {
        try {
            LOGGER.info("Download starting...");
            s3Client.download(new GetObjectRequest(bucketName, fileNameToLoad), fileWithAbsolutePathToLoad);
            LOGGER.info("Download complete.");
        } catch (Exception e) {
            throw new FileLoaderException("Error occurred while downloading file", e);
        }
    }
}
