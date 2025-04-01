package ru.practicum.shareit.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage> validationException(
      MethodArgumentNotValidException exception) {
    Map<String, String> result = new HashMap<>();
    exception.getBindingResult().getFieldErrors()
        .forEach(error -> result.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(result));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorMessage> exception(
      MethodArgumentNotValidException exception) {
    Map<String, String> result = new HashMap<>();

    result.put("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(result));
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage exception(NotFoundException exception) {
    Map<String, String> result = new HashMap<>();

    result.put("message", exception.getMessage());
    return new ErrorMessage(result);
  }


  @ExceptionHandler
  public ResponseEntity<ErrorMessage> exception(final Throwable exception) {
    Map<String, String> result = new HashMap<>();
    result.put("message", exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(result));
  }
}
