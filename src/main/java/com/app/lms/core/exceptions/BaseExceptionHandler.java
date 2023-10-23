package com.app.lms.core.exceptions;


import com.app.lms.core.errors.AppError;
import com.app.lms.core.utils.StringUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    StringUtil stringUtil = new StringUtil();

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity handleNotFoundException(NotFoundException exception) {
        AppError appError = new AppError(exception.getHttpStatus(), exception.getMessage());
        if (!exception.getDetails().isEmpty()) {
            appError.addAllSubError(exception.getDetails());
        } else {
            appError.addAllSubError(new ArrayList<>());
        }

        return new ResponseEntity(appError, exception.getHttpStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {

        AppError appError = new AppError(HttpStatus.FORBIDDEN, ex.getMessage());

        return new ResponseEntity(appError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>("JWT has expired", HttpStatus.UNAUTHORIZED);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .toList();

        AppError appError = new AppError(HttpStatus.BAD_REQUEST, "Ops!! Can't Process the Request");
        if (!ex.getFieldErrors().isEmpty()) {
            appError.addValidationErrors(errorList);
        } else {
            appError.addValidationErrors(new ArrayList<>());
        }

        return new ResponseEntity(appError, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParamsRequiredException.class)
    protected ResponseEntity handleParamsRequiredException(ParamsRequiredException exception) {
        AppError appError = new AppError(exception.getHttpStatus(), exception.getMessage());
        if (!exception.getDetails().isEmpty()) {
            appError.addAllSubError(exception.getDetails());
        } else {
            appError.addAllSubError(new ArrayList<>());
        }

        return new ResponseEntity(appError, exception.getHttpStatus());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoContentException.class)
    protected ResponseEntity handleNoContentException(NoContentException exception) {
        AppError appError = new AppError(exception.getHttpStatus(), exception.getMessage());
        if (!exception.getDetails().isEmpty()) {
            appError.addAllSubError(exception.getDetails());
        } else {
            appError.addAllSubError(new ArrayList<>());
        }

        return new ResponseEntity(appError, exception.getHttpStatus());
    }

    @ExceptionHandler(AppException.class)
    protected void handleAppException(AppException ex, HttpServletResponse response) {
        handleAppException(ex);
    }

    private AppException handleAppException(Exception exception) {
        if (exception instanceof AppException) {
            return (AppException) exception;
        } else if (exception instanceof IllegalArgumentException) {
            return new AppException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new AppException(exception.getMessage(), exception, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleException(Exception exception, HttpServletResponse response) {
        if (exception instanceof IOException) {
            log.error("IOEXception caused here");
            AppError appError = new AppError(HttpStatus.NO_CONTENT, exception.getMessage());
            return new ResponseEntity(appError, appError.getHttpStatus());
        }

        if (exception instanceof NoContentException) {
            log.error("No Content Exception caused here");
            AppError appError = new AppError(HttpStatus.NO_CONTENT, exception.getMessage());
            return new ResponseEntity(appError, appError.getHttpStatus());
        }

        if (exception instanceof NotFoundException) {
            log.error("Not Found Exception caused here");
            AppError appError = new AppError(HttpStatus.NOT_FOUND, exception.getMessage());
            return new ResponseEntity(appError, appError.getHttpStatus());
        }

        if (exception instanceof IllegalArgumentException || exception instanceof HttpException) {

            Throwable cause = exception.getCause();

            if (cause instanceof NotFoundException) {
                log.error("Data Not Found");
                AppError appError = new AppError(HttpStatus.NOT_FOUND, exception.getMessage());
                return new ResponseEntity(appError, appError.getHttpStatus());
            } else {

                log.error("Something went wrong");
                AppError appError = new AppError(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
                return new ResponseEntity(appError, appError.getHttpStatus());
            }
        }

        if (exception instanceof ExecutionException) {
            Throwable cause = exception.getCause();

            if (cause instanceof NotFoundException) {
                log.error("Notfound Exception caused here");
                AppError appError = new AppError(HttpStatus.NOT_FOUND, exception.getMessage());
                return new ResponseEntity(appError, appError.getHttpStatus());
            }

            if (cause instanceof NoContentException) {
                log.error("No Content Exception caused here of ExecutionException");
                AppError appError = new AppError(HttpStatus.NO_CONTENT, exception.getMessage());
                return new ResponseEntity(appError, appError.getHttpStatus());
            }
        }

        if (exception instanceof DataIntegrityViolationException) {
            String columnName = StringUtil.transformToCamelCase(extractConflictingValue((DataIntegrityViolationException) exception));
            String errorMessage = "Conflict on: " + columnName;
            log.error(errorMessage);
            AppError appError = new AppError(HttpStatus.CONFLICT, errorMessage);

            appError.addValidationError(null, columnName, null, errorMessage);
            return new ResponseEntity(appError, appError.getHttpStatus());
        }


        log.error("GENERAL Exception caused here: {}", exception.toString());
        AppError appError = new AppError(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity(appError, appError.getHttpStatus());
    }

    /**
     * Handle @Validated annotations
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity handleConstraintValidation(ConstraintViolationException exception) {
        AppError appError = new AppError(HttpStatus.BAD_REQUEST, "validation has been failed");
        appError.addValidationErrors(exception.getConstraintViolations());
        return new ResponseEntity(appError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle miss match data type
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity handleMethodArgumentMissmatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error(request.getParameterMap().toString());
        String _message = String.format("Parameter '%s' with value '%s', should be type of %s", ex.getName(),
                ex.getValue(), ex.getRequiredType().getSimpleName());
        AppError appError = new AppError(HttpStatus.BAD_REQUEST, _message);
        return new ResponseEntity(appError, HttpStatus.BAD_REQUEST);
    }

    private String extractConflictingValue(DataIntegrityViolationException ex) {
        // Extract the relevant information from the exception
        // You might need to inspect the exception message or use a specific exception subclass for more details
        // This depends on the specific database driver and the constraint that caused the violation
        // Example: extracting the violating value from a unique constraint violation
        String message = ex.getMostSpecificCause().getMessage();
        int startIndex = message.indexOf("Detail: Key (") + "Detail: Key (".length();
        int endIndex = message.indexOf(")=(", startIndex);
        return message.substring(startIndex, endIndex);
    }
}
