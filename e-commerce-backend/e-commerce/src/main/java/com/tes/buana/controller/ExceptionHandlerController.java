package com.tes.buana.controller;

import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.tes.buana.common.exception.BadRequestException;
import com.tes.buana.common.exception.BusinessException;
import com.tes.buana.common.exception.NotAuthenticatedException;
import com.tes.buana.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/*
 * This is a controller to handle exception.
 * Server response header should not return code 200 when exception thrown.
 * TODO: enhance with more func and flexibility from BaseController
 * */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleMyException(Exception ex, WebRequest req) {
        log.error(ex.getMessage(), ex);
        Response response = privateHandleException(ex);
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.valueOf(response.getStatus()), req);
    }

    private static Response privateHandleException(Throwable ex) {
        Response response;
        if(ex instanceof AccessDeniedException) {
            response = ResponseHelper.status(HttpStatus.FORBIDDEN);
        } else if (ex.getCause() instanceof BusinessException){
            response = ResponseHelper.status(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (ex instanceof NotFoundException || ex.getCause() instanceof NotFoundException) {
            response = ResponseHelper.status(HttpStatus.NOT_FOUND);
        }else if (ex.getCause() instanceof BadRequestException || ex.getCause() instanceof IllegalArgumentException) {
            response = ResponseHelper.status(HttpStatus.BAD_REQUEST);
        }else if (ex.getCause() instanceof NotAuthenticatedException) {
            response = ResponseHelper.status(HttpStatus.UNAUTHORIZED);
        }else {
            response = ResponseHelper.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setErrors(new HashMap() {{
            put("error", ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
        }});
        return response;
    }

    public static Response handleTestException(Throwable ex) {
        return privateHandleException(ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String errorMsg = "";
        for (FieldError fe : ex.getBindingResult().getFieldErrors() ) {
            errorMsg += errorMsg.length()<=0 ? fe.getDefaultMessage() : ","+ fe.getDefaultMessage();
        }
        Map<String,Object> response = new HashMap<>();
        response.put("error", "Parameter field invalid.");
        response.put("message", errorMsg);
        response.put("status", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getRequestPartName() + " parameter is missing";

        Map<String,Object> response = new HashMap<>();
        response.put("error", "Parameter field invalid.");
        response.put("message", error);
        response.put("status", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}
