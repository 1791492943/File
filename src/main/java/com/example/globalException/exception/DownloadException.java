package com.example.globalException.exception;

public class DownloadException extends RuntimeException{

    public DownloadException(String message) {
        super(message);
    }
}
