package com.example.exception;

public class FileExtNotMatchedException extends RuntimeException {
    public FileExtNotMatchedException() {} // RuntimeException 을 걸어야지만 롤백된다.
}
