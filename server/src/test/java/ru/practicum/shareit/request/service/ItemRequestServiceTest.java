package ru.practicum.shareit.request.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.UpdateItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

@DataJpaTest
@Import({UserServiceImpl.class, ItemRequestServiceImpl.class})
class ItemRequestServiceTest {

  @Autowired
  ItemRequestService itemRequestService;
  @Autowired
  UserService userService;

  User createUser01() {
    User user1 = User.builder()
        .name("test")
        .email("test@mail.ru").build();

    return userService.saveOrUpdate(user1);
  }


  @Test
  void create() {

    User user1 = createUser01();

    var createDto = CreateItemRequestDto.builder()
        .description("test").build();

    var result = itemRequestService.create(user1.getId(), createDto);

    assertNotNull(result);

  }

  @Test
  void update() {

    User user1 = createUser01();

    var createDto = CreateItemRequestDto.builder()
        .description("test").build();

    var result = itemRequestService.create(user1.getId(), createDto);

    assertNotNull(result);

    result.setDescription("update");
    var updateDto = UpdateItemRequestDto.builder()
        .description("update").build();

    itemRequestService.update(user1.getId(), updateDto);

    var dbResult = itemRequestService.getById(user1.getId());

    assertEquals(updateDto.getDescription(), dbResult.getDescription());

  }

  @Test
  void getUserRequests() {

    User user1 = createUser01();

    var createDto = CreateItemRequestDto.builder()
        .description("test").build();

    var result = itemRequestService.create(user1.getId(), createDto);
    itemRequestService.create(user1.getId(), createDto);

    assertNotNull(result);

    var dbResult = itemRequestService.getUserRequests(user1.getId());

    assertEquals(dbResult.size(), 2);
  }


  @Test
  void getById() {

    User user1 = createUser01();

    var createDto = CreateItemRequestDto.builder()
        .description("test").build();

    var result = itemRequestService.create(user1.getId(), createDto);

    assertNotNull(result);

    var dbResult = itemRequestService.getById(result.getId());

    assertEquals(result, dbResult);
  }
}
