package ru.practicum.shareit.item.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
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
    CreateItemDto itemDto = CreateItemDto.builder().name("item name")
        .description("item description").available(true).build();

    CreateUserDto userDto = CreateUserDto.builder().name("user name").email("mail").build();

    User user = UserMapper.toUser(userService.create(userDto));

    ItemDto createdItem = itemService.createItemWithOwnerId(itemDto, user.getId());

    assertNotNull(createdItem);
    assertNotNull(createdItem.getId());

    ItemDto itemDb = itemService.getById(createdItem.getId());
    assertNotNull(itemDb.getId());

    assertThrows(NotFoundException.class,
        () -> itemService.createItemWithOwnerId(itemDto, 0L));
  }

  @Test
  void testGetById() {

    assertThrows(NotFoundException.class, () -> itemService.getById(null));
    assertThrows(NotFoundException.class, () -> itemService.getById(-1L));
  }

  @Test
  void testGetAllByUserId() {
    CreateItemDto itemDto = CreateItemDto.builder().name("item name")
        .description("item description").available(true).build();

    CreateItemDto itemDto2 = CreateItemDto.builder().name("item name")
        .description("item description").available(true).build();

    CreateUserDto userDto = CreateUserDto.builder().name("user name").email("mail").build();

    User user = UserMapper.toUser(userService.create(userDto));

    ItemDto createdItem = itemService.createItemWithOwnerId(itemDto, user.getId());
    itemService.createItemWithOwnerId(itemDto2, user.getId());

    assertNotNull(createdItem);

    ItemDto itemDb = itemService.getById(createdItem.getId());
    assertNotNull(itemDb);

    List<ItemDto> itemsDb = itemService.getAllByUserId(user.getId());
    assertFalse(itemsDb.isEmpty());

    assertThrows(NotFoundException.class, () -> itemService.createItemWithOwnerId(
        CreateItemDto.builder()
            .requestId(0L)
            .build(),
        user.getId()
    ));

    assertThrows(NotFoundException.class, () -> itemService.createItemWithOwnerId(
        CreateItemDto.builder()
            .requestId(0L)
            .build(),
        0L
    ));

  }


  @Test
  void updateItemByIdWithOwnerId() {
    CreateItemDto itemDto = CreateItemDto.builder().name("item name")
        .description("item description").available(true).build();

    CreateUserDto ownerDto = CreateUserDto.builder().name("Owner").email("mail").build();
    User owner = UserMapper.toUser(userService.create(ownerDto));

    CreateUserDto userDto = CreateUserDto.builder().name("User").email("mail2").build();
    User user = UserMapper.toUser(userService.create(userDto));

    ItemDto createdItem = itemService.createItemWithOwnerId(itemDto, owner.getId());

    assertNotNull(createdItem);
    assertNotNull(createdItem.getId());
    UpdateItemDto updateItemDto = UpdateItemDto.builder().name("update name").description("update")
        .build();

    assertThrows(NotFoundException.class,
        () -> itemService.updateItemByIdWithOwnerId(999L, createdItem.getId(), updateItemDto));

    assertThrows(NotFoundException.class,
        () -> itemService.updateItemByIdWithOwnerId(owner.getId(), 999L, updateItemDto));

    assertThrows(NotFoundException.class,
        () -> itemService.updateItemByIdWithOwnerId(user.getId(), createdItem.getId(),
            updateItemDto));

    itemService.updateItemByIdWithOwnerId(
        owner.getId(),
        createdItem.getId(),
        UpdateItemDto.builder().available(true)
            .build()
    );

    itemService.updateItemByIdWithOwnerId(
        owner.getId(),
        createdItem.getId(),
        UpdateItemDto.builder().description("update")
            .build()
    );

    itemService.updateItemByIdWithOwnerId(
        owner.getId(),
        createdItem.getId(),
        UpdateItemDto.builder().name("new name")
            .build()
    );


  }

  @Test
  void search() {
    var result1 = itemService.search("");

    assertEquals(0, result1.size());

    CreateItemDto itemDto = CreateItemDto.builder().name("item name")
        .description("item description").available(true).build();

    CreateUserDto userDto = CreateUserDto.builder().name("user name").email("mail").build();

    User user = UserMapper.toUser(userService.create(userDto));

    itemService.createItemWithOwnerId(itemDto, user.getId());

    var result2 = itemService.search("test");

    assertEquals(0, result2.size());
  }

  @Test
  void getItemByIdAndOwnerId() {

    assertThrows(NotFoundException.class,
        () -> itemService.getItemByIdAndOwnerId(0L, 0L));

    CreateItemDto createItemDto = CreateItemDto.builder().name("item name")
        .description("item description").available(true).build();

    CreateUserDto ownerDto = CreateUserDto.builder().name("Owner").email("mail").build();
    var owner = UserMapper.toUser(userService.create(ownerDto));

    CreateUserDto userDto = CreateUserDto.builder().name("User").email("mail2").build();
    UserMapper.toUser(userService.create(userDto));

    itemService.createItemWithOwnerId(createItemDto, owner.getId());

    assertThrows(NotFoundException.class,
        () -> itemService.getItemByIdAndOwnerId(0L, owner.getId()));


  }
}
