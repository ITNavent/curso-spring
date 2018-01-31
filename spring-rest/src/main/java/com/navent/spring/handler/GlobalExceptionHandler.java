package com.navent.spring.handler;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.navent.spring.exceptions.NoDataException;
import com.navent.spring.exceptions.UserExistsException;

@ControllerAdvice //Controller para manejar excepciones en la app https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
@Component
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);		
	
	@ExceptionHandler // Este metodo se ejecuta ante la excepcion indicada
	@ResponseStatus(HttpStatus.NO_CONTENT) // codigo http que retorna
	public String handle(NoDataException ex) {
		log.debug("No data exception", ex);
		return null;
	}
	
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Error handle(UserExistsException ex) {
		log.error("Invalid user", ex);
		return error(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex, false);
	}

	@ExceptionHandler
	@ResponseBody // response un json
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Error handle(MethodArgumentNotValidException exception) {
		return error(HttpStatus.BAD_REQUEST.value(), String.join(", ",
				exception.getBindingResult().getFieldErrors()
					.stream()
					.map(FieldError::getDefaultMessage)
					.collect(Collectors.toList())),
				exception, false);
	}
	
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Error handle(ConstraintViolationException exception) {
		return error(HttpStatus.BAD_REQUEST.value(), String.join(",", 
				exception.getConstraintViolations()
					.stream()
					.map(ConstraintViolation::getMessage)
					.collect(Collectors.toList())), 
				exception, false);
	}
	
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Error handle(HttpServletRequest req, MethodArgumentTypeMismatchException ex) {
		log.error("Invalid argument exception", ex);
		return error(HttpStatus.BAD_REQUEST.value(), "Invalid Argument", ex, isDebugMode(req));
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Error handle(HttpServletRequest req, RuntimeException ex) {
		log.error("Internal Server Error", ex);
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", ex, isDebugMode(req));
	}
	
	

	private Error error(int code, String message, Exception ex, boolean debugMode) {
		Error er = new Error();
		er.setCode(code);
		er.setMessage(message);
		if (debugMode) {
			er.setStackTrace(ExceptionUtils.getStackTrace(ex));
		}
		return er;
	}

	private boolean isDebugMode(HttpServletRequest req) {
		return Boolean.valueOf(req.getParameter("debug_mode"));
	}
}
