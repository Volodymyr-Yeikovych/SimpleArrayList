package org.example;

public class MethodIsNotImplementedException extends RuntimeException{

    public MethodIsNotImplementedException() {
        super();
    }

    public MethodIsNotImplementedException(String message) {
        super(message);
    }
}
