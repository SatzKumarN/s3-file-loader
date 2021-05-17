package com.aws.s3.load;

interface AwsS3FileLoader {

    public void load(final String fileNameToLoad, final String fileWithAbsolutePathToLoad);
}
