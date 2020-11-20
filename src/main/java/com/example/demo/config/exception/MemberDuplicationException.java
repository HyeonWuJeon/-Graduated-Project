package com.example.demo.config.exception;

public class MemberDuplicationException extends RuntimeException {
    public MemberDuplicationException() {
    }

    public MemberDuplicationException(String message) {
        super(message);
    }
}
