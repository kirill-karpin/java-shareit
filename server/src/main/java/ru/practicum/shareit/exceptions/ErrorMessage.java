package ru.practicum.shareit.exceptions;

import java.util.Map;

public class ErrorMessage {

  public final Map<String, String> error;

  public ErrorMessage(Map<String, String> errors) {
    this.error = errors;
  }
}
