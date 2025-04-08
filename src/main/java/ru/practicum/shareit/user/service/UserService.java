package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {

  UserDto getById(Long userId);

  void delete(Long userId);

  UserDto create(CreateUserDto user);

  UserDto update(UpdateUserDto user);

  User saveOrUpdate(User user);
}
