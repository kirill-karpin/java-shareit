package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;

public interface UserService {

  User getById(Long userId);

  void delete(Long userId);

  User update(User user);

  User create(User user);
}
