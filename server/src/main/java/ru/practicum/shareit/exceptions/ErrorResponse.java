package ru.practicum.shareit.exceptions;

import lombok.Value;

@Value
public class ErrorResponse {
    private String error;
    private String description;
}
