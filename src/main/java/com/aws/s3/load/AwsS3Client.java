package com.aws.s3.load;

import java.io.Closeable;
import java.io.File;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

class AwsS3Client implements Closeable {

    private static final Logger LOGGER = Logger.getLogger(AwsS3Client.class);

    private final AmazonS3 s3client;

    private final TransferManager tm;

    AwsS3Client(final AmazonS3 s3client, int maxThreads) {
        this.s3client = s3client;
        this.tm = buildTransferManager(maxThreads);
    }

    public void upload(final PutObjectRequest request) throws AmazonClientException, InterruptedException {
        request.setGeneralProgressListener(buildProgressListener("Uploaded bytes: "));
        final Upload upload = tm.upload(request);
        upload.waitForCompletion();
    }

    private TransferManager buildTransferManager(int maxThreads) {
        return TransferManagerBuilder.standard().withS3Client(s3client)
                .withMultipartUploadThreshold((long) (5 * 1024 * 1025))
                .withExecutorFactory(() -> Executors.newFixedThreadPool(maxThreads)).build();
    }

    public void download(final GetObjectRequest request, final String fileNameWithPath)
            throws AmazonClientException, InterruptedException {
        request.setGeneralProgressListener(buildProgressListener("Downloaded bytes: "));
        final Download download = tm.download(request, new File(fileNameWithPath));
        download.waitForCompletion();
    }

    private ProgressListener buildProgressListener(final String messageToDisplay) {
        return progressEvent -> LOGGER.info(messageToDisplay + progressEvent.getBytesTransferred());
    }

    @Override
    public void close() {
        tm.shutdownNow();
    }
}
