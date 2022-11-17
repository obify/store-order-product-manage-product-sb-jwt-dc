package com.assignment.api.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.assignment.api.constants.CodeMessage;
import com.assignment.api.exception.ExistException;
import com.assignment.api.exception.NotFoundException;
import com.assignment.api.exception.OrderPlaceFailed;
import com.assignment.api.model.APIResponse;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex) {
		APIResponse responseModel = new APIResponse();
		responseModel.setCode(CodeMessage.exception);
		responseModel.setMessage(CodeMessage.exceptionMessage);
		ex.printStackTrace();
		return new ResponseEntity(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ExistException.class)
	public APIResponse handleExistException(ExistException ex) {
		APIResponse responseModel = new APIResponse();
		responseModel.setCode(CodeMessage.USER_EXIST);
		responseModel.setMessage(ex.getMessage());
		return responseModel;
	}

	@ExceptionHandler(NotFoundException.class)
	public APIResponse handleNotFoundException(NotFoundException ex) {
		APIResponse responseModel = new APIResponse();
		responseModel.setCode(CodeMessage.NO_RECORDS);
		responseModel.setMessage(ex.getMessage());
		return responseModel;
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public APIResponse handleUsernameNotFoundException(UsernameNotFoundException ex) {
		APIResponse responseModel = new APIResponse();
		responseModel.setCode(CodeMessage.INCORRECT_USER);
		responseModel.setMessage(ex.getMessage());
		return responseModel;
	}

	@ExceptionHandler(OrderPlaceFailed.class)
	public APIResponse handleOrderPlaceFailedException(OrderPlaceFailed ex) {
		APIResponse responseModel = new APIResponse();
		responseModel.setCode(CodeMessage.FAIL_ORDER);
		responseModel.setMessage(ex.getMessage());
		return responseModel;
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public APIResponse handleExpiredJwtExceptionException(ExpiredJwtException ex) {
		APIResponse responseModel = new APIResponse();
		responseModel.setCode(CodeMessage.TOKEN_EXPIRED);
		responseModel.setMessage(ex.getMessage());
		return responseModel;
	}

	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
	}
}
