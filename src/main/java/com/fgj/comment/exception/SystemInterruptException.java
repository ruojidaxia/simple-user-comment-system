package com.fgj.comment.exception;

public class SystemInterruptException extends RuntimeException {
    public SystemInterruptException() {
        super();
    }

    public SystemInterruptException(String message) {
        super(message);
    }

    public SystemInterruptException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemInterruptException(Throwable cause) {
        super(cause);
    }

    protected SystemInterruptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
