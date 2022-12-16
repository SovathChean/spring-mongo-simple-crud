package com.sovathc.mongodemocrud.common.exception;

import com.sovathc.mongodemocrud.common.constants.SysHttpResultCode;
import com.sovathc.mongodemocrud.common.controller.ResponseMessage;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public abstract class AbstractException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        super.handleExceptionInternal(ex, body, headers, status, request);
        if (ex instanceof HttpMessageNotReadableException)
            return handleBadRequestException(ex, status);
        logger.error("Error internal 500{}", ex);

        return this.buildExceptionResponse(ex, HttpStatus.BAD_REQUEST, SysHttpResultCode.ERROR_500.getCode());
    }
    protected ResponseEntity<ResponseMessage<?>> handleBadRequestException(Exception ex, HttpStatus status)
    {
        return this.buildExceptionResponse(ex, status, SysHttpResultCode.ERROR_400.getCode());
    }
    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<ResponseMessage<?>> handleDataAccessException(DataAccessException ex) {
        logger.error("DataAccess exception: {}", ex);
        String message = "error cannot read or write data";
        if (ex instanceof DataIntegrityViolationException)
            message = "error cannot save data";
        else if (ex instanceof InvalidDataAccessApiUsageException)
            message = "error cannot read data";

        return buildExceptionResponseMessage(message, HttpStatus.BAD_REQUEST, SysHttpResultCode.ERROR_400.getCode());
    }
    public ResponseEntity<ResponseMessage<?>> buildExceptionResponse(Exception ex, HttpStatus status, String resultCode)
    {
        return buildExceptionResponseMessage(ex.getMessage(), status, resultCode);
    }
    public ResponseEntity<ResponseMessage<?>> buildExceptionResponseMessage(String message, HttpStatus status, String resultCode)
    {
        ResponseMessage<?> response = new ResponseMessage<>();
        response.setError(message);
        response.setResult(false);
        response.setStatus(status);
        response.setResultCode(resultCode);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
