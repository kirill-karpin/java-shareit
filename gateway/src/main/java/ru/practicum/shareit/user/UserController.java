package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {

  private final UserClient userClient;

  public UserController(UserClient userClient) {
    this.userClient = userClient;
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> get(@PathVariable Long userId) {
    return userClient.getById(userId);
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody @Valid CreateUserDto user) {
    return userClient.create(user);
  }

  @PatchMapping("/{userId}")
  public ResponseEntity<?> update(@PathVariable Long userId,
      @RequestBody UpdateUserDto userUpdateDto) {
    return userClient.update(userId, userUpdateDto);
  }

  @DeleteMapping("/{userId}")
  public void delete(@PathVariable Long userId) {
    userClient.delete(userId);
  }
}
