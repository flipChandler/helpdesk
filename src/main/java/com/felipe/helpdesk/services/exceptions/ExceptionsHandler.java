package com.felipe.helpdesk.services.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.felipe.helpdesk.util.MessageUtils;

@ControllerAdvice // controla essa classe quando uma exceção for lançada
public class ExceptionsHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
			HttpServletRequest request) {
		
		StandardError error = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.NOT_FOUND.value(),
				MessageUtils.OBJECT_NOT_FOUND,
				ex.getMessage(),
				request.getRequestURI());		
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);		
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex,
			HttpServletRequest request) {
		
		StandardError error = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(),
				MessageUtils.DATA_INTEGRITY_VIOLATION,
				ex.getMessage(),
				request.getRequestURI());		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);		
	}	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		
		ValidationError errors = new ValidationError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(),
				MessageUtils.ARGUMENT_NOT_VALID,
				MessageUtils.VALIDATION_ERROR_MESSAGE,
				request.getRequestURI());
		
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errors.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);		
	}
	
}
