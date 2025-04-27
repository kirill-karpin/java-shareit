package ru.practicum.shareit.booking.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingDtoJsonTest {

  private final JacksonTester<BookingDto> json;

  @Test
  void testBookingDto() throws Exception {
    BookingDto bookingDto = BookingDto
        .builder()
        .id(1L)
        .start(LocalDateTime.now())
        .end(LocalDateTime.now().plusDays(1))
        .item(new ItemDto())
        .booker(new UserDto())
        .status(BookingStatus.APPROVED)
        .build();

    JsonContent<BookingDto> result = json.write(bookingDto);

    assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
    assertThat(result).extractingJsonPathStringValue("$.start").isNotEmpty();
    assertThat(result).extractingJsonPathStringValue("$.end").isNotEmpty();
    assertThat(result).extractingJsonPathStringValue("$.status")
        .isEqualTo(String.valueOf(BookingStatus.APPROVED));
  }
}
