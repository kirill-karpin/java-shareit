package ru.practicum.shareit.item.service;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

@DataJpaTest
@Import({BookingServiceImpl.class, ItemServiceImpl.class, UserServiceImpl.class,
    CommentServiceImpl.class})
class CommentServiceImplTest {

  @Autowired
  private UserService userService;

  @Autowired
  private CommentService commentService;

  @Autowired
  private ItemService itemService;

  @Autowired
  private BookingServiceImpl bookingService;

  @Test
  void commentBookingPast() {

    CreateCommentDto commentDto = CreateCommentDto.builder()
        .text("text")
        .build();

    CreateUserDto userDto = CreateUserDto.builder()
        .name("userName")
        .email("email")
        .build();

    CreateItemDto itemDto = CreateItemDto.builder()
        .name("itemName")
        .description("itemDescription")
        .available(true)
        .build();

    UserDto userDto1 = userService.create(userDto);

    ItemDto itemDto1 = itemService.createItemWithOwnerId(itemDto, userDto1.getId());

    assertThrows(RuntimeException.class,
        () -> commentService.commentBookingPast(userDto1.getId(), itemDto1.getId(),
            commentDto));

    CreateBookingDto bookingDto = CreateBookingDto.builder()
        .itemId(itemDto1.getId())
        .start(LocalDateTime.now().minusDays(5))
        .end(LocalDateTime.now().minusDays(4))
        .build();

    BookingDto bookingDto1 = bookingService.create(userDto1.getId(), bookingDto);
    bookingService.approve(userDto1.getId(), bookingDto1.getId(), true);

    assertDoesNotThrow(() -> {
      commentService.commentBookingPast(userDto1.getId(), itemDto1.getId(), commentDto);
    });
  }

  @Test
  void commentBookingFutureThrowException() {

    CreateCommentDto commentDto = CreateCommentDto.builder()
        .text("text")
        .build();

    CreateUserDto userDto = CreateUserDto.builder()
        .name("userName")
        .email("email")
        .build();

    CreateItemDto itemDto = CreateItemDto.builder()
        .name("itemName")
        .description("itemDescription")
        .available(true)
        .build();

    UserDto userDto1 = userService.create(userDto);

    ItemDto itemDto1 = itemService.createItemWithOwnerId(itemDto, userDto1.getId());

    CreateBookingDto bookingDto = CreateBookingDto.builder()
        .itemId(itemDto1.getId())
        .start(LocalDateTime.now().minusDays(5))
        .end(LocalDateTime.now().plusDays(4))
        .build();

    BookingDto bookingDto1 = bookingService.create(userDto1.getId(), bookingDto);
    bookingService.approve(userDto1.getId(), bookingDto1.getId(), true);

    assertThrows(RuntimeException.class,
        () -> commentService.commentBookingPast(userDto1.getId(), itemDto1.getId(), commentDto));
  }
}
