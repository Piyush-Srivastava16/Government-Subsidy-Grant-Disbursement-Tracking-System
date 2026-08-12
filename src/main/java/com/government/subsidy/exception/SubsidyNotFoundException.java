package com.government.subsidy.exception;

public class SubsidyNotFoundException extends RuntimeException {
//    SubsidyNotFoundException extends Java's built-in RuntimeException ---->means our custom exception behaves like a runtime exception.
    public SubsidyNotFoundException(String message) {
        super(message);
    }
}