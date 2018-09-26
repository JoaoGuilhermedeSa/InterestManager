package com.SRMAsset.InterestManager.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);


	public RestResponseEntityExceptionHandler() {
		super();
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName() + " em " + request.getContextPath());
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, errorResponse, headers, errorResponse.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName() + " em " + request.getContextPath());
		//
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, errors);
		return handleExceptionInternal(ex, errorResponse, headers, errorResponse.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName() + " em " + request.getContextPath());
		final String error = "O valor " + ex.getValue() + " do atributo " + ex.getPropertyName()
				+ " deve ser do tipo " + ex.getRequiredType().getSimpleName();

		final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, error);
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName() + " em " + request.getContextPath());
		final String error = ex.getRequestPartName() + " part is missing";
		final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, error);
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		log.info(ex.getClass().getName() + " em " + request.getContextPath());
		final String error = "O parâmetro " + ex.getParameterName() + " está ausente";
		final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, error);
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		log.info(ex.getClass().getName() + " em " + request.getContextPath());
		final String error = "Parâmetro " + ex.getName() + " deve ser um " + ex.getRequiredType().getSimpleName();

		final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, error);
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info(ex.getClass().getName() + " em " + request.getContextPath());
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, error);
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		log.info(ex.getClass().getName() + " em " + request.getContextPath());
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append("Este método não está disponível para esta requisição. Métodos disponíveis são:");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, builder.toString());
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		log.error(ex.getClass().getName() + " em " + request.getContextPath(), ex);
		final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Houve um erro inesperado");
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}

}
