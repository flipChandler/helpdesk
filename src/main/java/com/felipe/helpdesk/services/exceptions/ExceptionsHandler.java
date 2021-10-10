package com.felipe.helpdesk.services.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.felipe.helpdesk.util.MessageUtils;

@ControllerAdvice // controla essa classe quando uma exceção for lançada
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<StandardError> objectNotFoundException(BusinessException ex,
			HttpServletRequest request) {
		
		StandardError error = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.NOT_FOUND.value(),
				MessageUtils.OBJECT_NOT_FOUND,
				ex.getMessage(),
				request.getRequestURI());		
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);		
	}
}
