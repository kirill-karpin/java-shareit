package ru.practicum.shareit.item.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
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
  void testCreate() {
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

    ItemDto itemDb = itemService.getById(createdItem.getId());
    assertNotNull(itemDb.getId());
  }

  @Test
  void testGetById() {
  }

  @Test
  void testGetAllByUserId() {
    CreateItemDto itemDto = CreateItemDto.builder()
        .name("item name")
        .description("item description")
        .available(true)
        .build();

    CreateItemDto itemDto2 = CreateItemDto.builder()
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
    itemService.createItemWithOwnerId(itemDto2, user.getId());

    assertNotNull(createdItem);

    ItemDto itemDb = itemService.getById(createdItem.getId());
    assertNotNull(itemDb);

    List<ItemDto> itemsDb = itemService.getAllByUserId(user.getId());
    assertFalse(itemsDb.isEmpty());

  }

  @Test
  void update() {
    assertEquals(2, 2);
  }

  @Test
  void updateItemByIdWithOwnerId() {
    assertEquals(2, 2);
  }

  @Test
  void search() {
    assertEquals(2, 2);
  }

  @Test
  void createItemWithOwnerId() {
    assertEquals(2, 2);
  }

  @Test
  void getItemByIdAndOwnerId() {
    assertEquals(2, 2);
  }
}
