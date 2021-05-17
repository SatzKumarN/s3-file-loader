package com.aws.s3.load;

class AwsS3FileLoaderFactory {

    private final AwsS3Client s3Client;

    private final String bucketName;

    AwsS3FileLoaderFactory(final AwsS3Client s3Client, final String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public AwsS3FileLoader getInstance(final String loadFlag) {
        if ("upload".equalsIgnoreCase(loadFlag))
            return new AwsS3FileUploader(s3Client, bucketName);
        else if ("download".equalsIgnoreCase(loadFlag))
            return new AwsS3FileDownloader(s3Client, bucketName);
        else
            throw new FileLoaderException("Unsupported operation");
    }
}
