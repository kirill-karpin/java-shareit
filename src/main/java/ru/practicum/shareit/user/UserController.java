package ru.practicum.shareit.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.service.UserService;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{userId}")
  public User get(@PathVariable Long userId) {
    return userService.getById(userId);
  }

  @PostMapping
  public User create(@RequestBody User user) {
    return userService.create(user);
  }

  @PatchMapping("/{userId}")
  public User update(@PathVariable Long userId, @RequestBody UpdateUserDto userUpdateDto) {
    User user = userService.getById(userId);

    if (user == null) {
      throw new NotFoundException("Пользователь с id " + userId + " не найден");
    }

    if (userUpdateDto.getName() != null) {
      user.setName(userUpdateDto.getName());
    }
    if (userUpdateDto.getEmail() != null) {
      user.setEmail(userUpdateDto.getEmail());
    }

    return userService.update(user);
  }

  @DeleteMapping("/{userId}")
  public void delete(@PathVariable Long userId) {
    userService.delete(userId);
  }
}
