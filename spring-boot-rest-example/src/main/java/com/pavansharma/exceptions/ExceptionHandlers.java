package com.pavansharma.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pavansharma.utilities.APIError;

@RestControllerAdvice
public class ExceptionHandlers {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIError> handleResourceNotFound(ResourceNotFoundException exception){
		APIError error = new APIError(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity < ? > globleExcpetionHandler(Exception ex) {
		APIError errorModel = new APIError(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
		return new ResponseEntity < > (errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
