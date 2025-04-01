package ru.practicum.shareit.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getById(Long userId) {
    UserDto userDto = userRepository.findById(userId);
    if (userDto == null) {
      throw new NotFoundException(
          String.format("User with id %d not found", userId)
      );
    }
    return UserMapper.toUser(userDto);
  }

  @Override
  public void delete(Long userId) {
    userRepository.delete(userId);
  }

  @Override
  public User update(User user) {
    UserDto userDto = userRepository.save(UserMapper.toUserDto(user));
    return UserMapper.toUser(userDto);
  }

  @Override
  public User create(User user) {
    UserDto userDto = userRepository.save(UserMapper.toUserDto(user));
    return UserMapper.toUser(userDto);
  }
}
