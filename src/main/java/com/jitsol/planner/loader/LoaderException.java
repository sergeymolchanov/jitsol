package com.jitsol.planner.loader;

public class LoaderException extends Exception {
    public LoaderException() {
    }

    public LoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoaderException(String message) {
        super(message);
    }

    public LoaderException(Throwable cause) {
        super(cause);
    }
}
