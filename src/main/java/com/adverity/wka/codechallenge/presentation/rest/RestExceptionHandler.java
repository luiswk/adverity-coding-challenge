package com.adverity.wka.codechallenge.presentation.rest;

import java.util.Collections;
import java.util.List;

import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Wojciech Kaczmarek
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return Collections.singletonList(ex.getMessage());
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> handleDataAccessException(InvalidDataAccessResourceUsageException ex) {
        return Collections.singletonList("Invalid aggregate on/group by request!");
    }

}
