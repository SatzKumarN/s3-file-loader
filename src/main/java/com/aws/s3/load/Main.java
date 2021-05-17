package com.aws.s3.load;

import static java.lang.System.exit;

import org.apache.log4j.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    /**
     * args[0] | value can be either upload or download
     * 
     * args[1] | aws properties file absolute path (AWS_ACCESS_KEY_ID,
     * AWS_SECRET_ACCESS_KEY ,AWS_REGION, AWS_BUCKET_NAME)
     * 
     * args[2] | fileName which is either uploaded or downloaded based on arg[0]
     * 
     * args[3] | absolute file path of file in local which is either to be
     * uploaded or will be downloaded in the same name based on arg[0]
     * 
     */
    public static void main(String[] args) {
        try {
            doFileLoadOperation(args[0], new PropertiesReader(args[1]), args[2], args[3]);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while file operation", e);
        }
        exit(0);
    }

    private static void doFileLoadOperation(final String loadFlag, final PropertiesReader reader,
            final String fileNameToLoad, final String filePathToLoad) {
        final AwsS3Client s3Client = buildS3Client(reader);
        final AwsS3FileLoaderFactory factory = new AwsS3FileLoaderFactory(s3Client, reader.get("AWS_BUCKET_NAME"));
        final AwsS3FileLoader fileLoader = factory.getInstance(loadFlag);
        fileLoader.load(fileNameToLoad, filePathToLoad);
    }

    private static AwsS3Client buildS3Client(final PropertiesReader reader) {
        return new AwsS3Client(new AmazonS3Factory(reader).getInstance(), 50);
    }
}
