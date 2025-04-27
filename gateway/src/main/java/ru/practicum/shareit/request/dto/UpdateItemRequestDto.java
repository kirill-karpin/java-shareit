package ru.practicum.shareit.request.dto;

import lombok.Data;
import ru.practicum.shareit.user.dto.UserDto;

/**
 * TODO Sprint add-item-requests.
 */
@Data
public class UpdateItemRequestDto {

  private Long id;
  private UserDto requestor;
  private String description;
}
