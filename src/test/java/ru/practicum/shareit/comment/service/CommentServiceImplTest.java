package ru.practicum.shareit.comment.service;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.service.ItemServiceImpl;
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

    CommentDto result = commentService.commentBookingPast(userDto1.getId(), itemDto1.getId(),
        commentDto);

    assertNotNull(result);

    List<ItemDto> items = itemService.getAllByUserId(userDto1.getId());

    assertTrue(items.size() == 1);
  }
}
