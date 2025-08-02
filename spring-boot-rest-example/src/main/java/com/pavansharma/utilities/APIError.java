package com.pavansharma.utilities;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class APIError {

	private int status;
    private String error;
    private LocalDateTime timestamp;

    public APIError(HttpStatus status, String error) {
    	super();
        this.status = status.value();
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
    
    
}
