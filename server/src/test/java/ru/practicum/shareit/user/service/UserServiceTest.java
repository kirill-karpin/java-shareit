package ru.practicum.shareit.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

@DataJpaTest
@Import({UserServiceImpl.class})
class UserServiceTest {

  @Autowired
  private UserService userService;

  @Test
  void create() {
    User user1 = User.builder().name("test").email("test@mail.ru").build();

    User userDto = userService.saveOrUpdate(user1);

    assertTrue(userDto.getId() > 0);
  }

  @Test
  void uniqueEmail() {
    User user1 = User.builder().name("test").email("test@mail.ru").build();

    userService.saveOrUpdate(user1);

    User user2 = User.builder().name("test").email("test@mail.ru").build();

    assertThrows(RuntimeException.class, () -> userService.saveOrUpdate(user2));
  }

  @Test
  void update() {
    User user1 = User.builder().name("test").email("test@mail.ru").build();

    User createdUserDto = userService.saveOrUpdate(user1);
    assertTrue(createdUserDto.getId() > 0);

    UpdateUserDto updateUserDto = UpdateUserDto.builder().name("test2").build();

    UserMapper.merge(updateUserDto, user1);

    userService.saveOrUpdate(user1);

    UserDto userDb = userService.getById(user1.getId());

    assertEquals(user1.getName(), userDb.getName());

    UpdateUserDto updateUserDto2 = UpdateUserDto.builder().email("test2@mail.ru").build();

    UserMapper.merge(updateUserDto2, user1);

    userService.saveOrUpdate(user1);

    UserDto userDb2 = userService.getById(user1.getId());

    assertEquals(user1.getEmail(), userDb2.getEmail());

  }
}
