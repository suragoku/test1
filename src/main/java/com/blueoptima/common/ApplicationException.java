package com.blueoptima.common;

/**
 * Created by Suranjay on 10/07/18.
 */
public class ApplicationException extends Exception {

    private Throwable cause;

    public ApplicationException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

}
