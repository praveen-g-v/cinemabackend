package com.nehruCollege.cinema.utils;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String errorCode;
    private List<String> details;
    private String path;

    public ErrorResponse(String message, String errorCode, List<String> details, String path) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.errorCode = errorCode;
        this.details = details;
        this.path = path;
    }

    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getMessage() { return message; }
    public String getErrorCode() { return errorCode; }
    public List<String> getDetails() { return details; }
    public String getPath() { return path; }
}