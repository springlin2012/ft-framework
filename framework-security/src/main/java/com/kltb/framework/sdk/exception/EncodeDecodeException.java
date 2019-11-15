
package com.kltb.framework.sdk.exception;


public class EncodeDecodeException extends Exception {
    private static final long serialVersionUID = 6741485269560471282L;

    public EncodeDecodeException() {
    }

    public EncodeDecodeException(String message, Throwable t) {
        super(message, t);
    }

    public EncodeDecodeException(String message) {
        super(message);
    }
}
