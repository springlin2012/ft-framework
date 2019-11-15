// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst 
// Source File Name:   SignException.java

package com.kltb.framework.sdk.exception;


public class SignException extends Exception {
    private static final long serialVersionUID = -6999324779241916632L;

    public SignException() {
    }

    public SignException(String message, Throwable t) {
        super(message, t);
    }

    public SignException(String message) {
        super(message);
    }
}
