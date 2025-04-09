package ru.practicum.shareit.comment.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

  private Long id;
  private String text;
  private String authorName;
  private LocalDateTime created;

}
