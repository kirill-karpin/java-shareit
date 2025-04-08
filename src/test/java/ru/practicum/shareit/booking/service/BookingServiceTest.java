package ru.practicum.shareit.booking.service;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

@DataJpaTest
@Import({BookingServiceImpl.class, ItemServiceImpl.class, UserServiceImpl.class})
class BookingServiceTest {

  @Autowired
  private BookingService bookingService;
  @Autowired
  private UserService userService;
  @Autowired
  private ItemService itemService;

  @BeforeEach
  void setUp() {

  }


  @Test
  void create() {

    CreateUserDto createUserDto = CreateUserDto.builder().name("name").email("mail@mail.ru")
        .build();
    UserDto userDto = userService.create(createUserDto);

    CreateItemDto createItemDto = CreateItemDto.builder()
        .name("item")
        .available(true)
        .description("item").build();

    ItemDto itemDto = itemService.create(userDto.getId(), createItemDto);

    CreateBookingDto bookingDto = CreateBookingDto.builder()
        .start(LocalDateTime.parse("2023-08-14T16:00:00"))
        .end(LocalDateTime.parse("2023-08-14T17:00:00"))
        .itemId(itemDto.getId())
        .build();

    BookingDto dto = bookingService.create(userDto.getId(), bookingDto);

    assertNotNull(dto);
  }
}
