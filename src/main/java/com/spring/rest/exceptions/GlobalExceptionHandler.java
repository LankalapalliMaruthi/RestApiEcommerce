package com.spring.rest.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse>exceptionHandling(EmployeeNotFoundException exception){
       EmployeeErrorResponse er=new EmployeeErrorResponse();
       er.setTimeStamp(LocalDateTime.now());
       er.setStatus((HttpStatus.NOT_FOUND.value()));
       er.setMsg(exception.getMessage());
       return ResponseEntity.status(HttpStatus.NOT_FOUND)
    		   .body(er);
}
	@ExceptionHandler
	public ResponseEntity<Map<String,String>>exceptionhandler2(MethodArgumentNotValidException exception){
		Map<String, String>errorMap=new HashMap<>();
		exception.getBindingResult()
		.getFieldErrors()
		.forEach(error->{
			String name=error.getField();
			String msg=error.getDefaultMessage();
			errorMap.put(name, msg);
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(errorMap);
}
}
