package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.dto.UserDto;

public class UserMapper {

  public static UserDto toUserDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setEmail(user.getEmail());

    return userDto;
  }

  public static User toUser(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    return user;
  }

  public static User toUser(CreateUserDto userDto) {
    User user = new User();
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    return user;
  }

  public static void merge(UpdateUserDto userDto, User user) {
    user.setName((userDto.getName() != null ? userDto.getName() : user.getName()));
    user.setEmail((userDto.getEmail() != null ? userDto.getEmail() : user.getEmail()));
  }

}
