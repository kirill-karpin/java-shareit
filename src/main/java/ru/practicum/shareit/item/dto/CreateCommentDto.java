package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.Past;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentDto {

  private Long itemId;

  private String text;

  @Past()
  private LocalDateTime created;

}
