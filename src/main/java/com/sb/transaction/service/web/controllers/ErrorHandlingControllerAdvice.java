package com.sb.transaction.service.web.controllers;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.sb.transaction.service.exception.ErrorMessage;
import com.sb.transaction.service.exception.FutureTime;
import com.sb.transaction.service.exception.NotFound;
import com.sb.transaction.service.exception.TimeExceeded;

@RestControllerAdvice
class ErrorHandlingControllerAdvice {

	@ExceptionHandler(TimeExceeded.class)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ErrorMessage onTimeExceeded(TimeExceeded ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NO_CONTENT.value(), OffsetDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return message;
	}

	@ExceptionHandler(FutureTime.class)
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	public ErrorMessage onFutureTime(FutureTime ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), OffsetDateTime.now(),
				ex.getMessage(), request.getDescription(false));
		return message;
	}
	
	@ExceptionHandler(NotFound.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage onNotFound(NotFound ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), OffsetDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return message;
	}

}
