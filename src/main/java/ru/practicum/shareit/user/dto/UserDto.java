package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class UserDto {

  private Long id;

  private String name;
  @Email
  private String email;

}
