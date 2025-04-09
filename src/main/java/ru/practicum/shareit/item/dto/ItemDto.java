package ru.practicum.shareit.item.dto;

import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

  private Long id;
  private String name;
  private String description;
  private Boolean available;
  private List<CommentDto> comments = new LinkedList<>();
  private BookingDto nextBooking;
  private BookingDto lastBooking;
}
