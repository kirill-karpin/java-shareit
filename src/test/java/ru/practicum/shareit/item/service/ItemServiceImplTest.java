package ru.practicum.shareit.item.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

@DataJpaTest
@Import({ItemServiceImpl.class, UserServiceImpl.class})
class ItemServiceImplTest {

  @Autowired
  private ItemService itemService;
  @Autowired
  private UserService userService;


  @Test
  void create() {
    CreateItemDto itemDto = CreateItemDto.builder()
        .name("item name")
        .description("item description")
        .available(true)
        .build();

    CreateUserDto userDto = CreateUserDto.builder()
        .name("user name")
        .email("mail")
        .build();

    User user = UserMapper.toUser(userService.create(userDto));

    ItemDto createdItem = itemService.createItemWithOwnerId(itemDto, user.getId());

    assertNotNull(createdItem);
    assertNotNull(createdItem.getId());

    ItemDto itemDb = itemService.getById(user.getId());
    assertNotNull(itemDb.getId());
  }

  @Test
  void getById() {
  }

  @Test
  void getAllByUserId() {
  }

  @Test
  void update() {
  }

  @Test
  void updateItemByIdWithOwnerId() {
  }

  @Test
  void search() {
  }

  @Test
  void createItemWithOwnerId() {
  }

  @Test
  void getItemByIdAndOwnerId() {
  }
}
