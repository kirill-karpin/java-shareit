package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;
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
  public UserDto get(@PathVariable Long userId) {
    return userService.getById(userId);
  }

  @PostMapping
  public UserDto create(@RequestBody @Valid CreateUserDto user) {
    return userService.create(user);
  }

  @PatchMapping("/{userId}")
  public UserDto update(@PathVariable Long userId, @RequestBody UpdateUserDto userUpdateDto) {

    return userService.update(userId, userUpdateDto);
  }

  @DeleteMapping("/{userId}")
  public void delete(@PathVariable Long userId) {
    userService.delete(userId);
  }
}
