package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO Sprint add-controllers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateItemDto {

  @NotNull
  @NotEmpty
  private String name;
  @NotNull
  @NotEmpty
  private String description;
  @NotNull
  private Boolean available;
}
