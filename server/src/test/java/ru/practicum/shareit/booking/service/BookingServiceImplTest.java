package ru.practicum.shareit.booking.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exceptions.NotFoundException;
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
class BookingServiceImplTest {

  @Autowired
  private BookingService bookingService;
  @Autowired
  private UserService userService;
  @Autowired
  private ItemService itemService;

  @Test
  void createAndApproveBookingTest() {

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

    bookingService.approve(userDto.getId(), dto.getId(), true);

    BookingDto approvedDto = bookingService.getById(dto.getId());
    assertEquals(BookingStatus.APPROVED, approvedDto.getStatus());

    bookingService.approve(userDto.getId(), dto.getId(), false);
    BookingDto approvedfalseDto = bookingService.getById(dto.getId());

    assertEquals(BookingStatus.REJECTED, approvedfalseDto.getStatus());
  }

  @Test
  void createAndApproveBookingWrongUserTest() {

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

    assertThrows(NotFoundException.class,
        () -> bookingService.create(10000L, bookingDto));
  }

  @Test
  void getByOwnerIdOrBookerId() {
    CreateUserDto createUserDto = CreateUserDto.builder().name("name1").email("mail@mail.ru")
        .build();
    UserDto ownerDto = userService.create(createUserDto);

    CreateItemDto createItemDto = CreateItemDto.builder()
        .name("item")
        .available(true)
        .description("item")
        .build();

    ItemDto itemDto = itemService.create(ownerDto.getId(), createItemDto);

    CreateUserDto bookerDto = CreateUserDto.builder().name("name2").email("mail2@mail.ru")
        .build();
    UserDto booker = userService.create(bookerDto);

    CreateBookingDto bookingDto = CreateBookingDto.builder()
        .start(LocalDateTime.parse("2023-08-14T16:00:00"))
        .end(LocalDateTime.parse("2023-08-14T17:00:00"))
        .itemId(itemDto.getId())
        .build();

    BookingDto dto = bookingService.create(booker.getId(), bookingDto);

    BookingDto bookingDto1 = bookingService.getByOwnerIdOrBookerId(dto.getId(), booker.getId());
    assertNotNull(bookingDto1);

    BookingDto bookingDto2 = bookingService.getByOwnerIdOrBookerId(dto.getId(), ownerDto.getId());
    assertNotNull(bookingDto2);

    assertEquals(bookingDto1.getId(), bookingDto2.getId());

    assertEquals(dto.getId(), bookingDto1.getId());
    assertEquals(dto.getId(), bookingDto2.getId());

  }


  @Test
  void getBookingByBookerId() {
    assertThrows(NotFoundException.class,
        () -> bookingService.getBookingByBookerId(0L));
  }

  @Test
  void getBookingByOwnerId() {

    assertThrows(NotFoundException.class,
        () -> bookingService.getBookingByOwnerId(0L));
  }
}
