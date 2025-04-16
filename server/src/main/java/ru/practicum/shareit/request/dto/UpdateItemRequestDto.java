package ru.practicum.shareit.request.dto;

import lombok.Data;
import ru.practicum.shareit.user.User;

/**
 * TODO Sprint add-item-requests.
 */
@Data
public class UpdateItemRequestDto {

  private Long id;
  private User requestor;
  private String description;
}
