package com.aws.s3.load;

class FileLoaderException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileLoaderException(final String message) {
        super(message);
    }

    public FileLoaderException(final String message, final Throwable e) {
        super(message, e);
    }

}
