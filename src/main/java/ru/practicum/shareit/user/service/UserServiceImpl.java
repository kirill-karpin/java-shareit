package ru.practicum.shareit.user.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
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
  public UserDto getById(Long userId) {
    Optional<User> userDto = userRepository.findById(userId);
    if (userDto.isEmpty()) {
      throw new NotFoundException(String.format("User with id %d not found", userId));
    }
    return UserMapper.toUserDto(userDto.get());
  }

  @Override
  public void delete(Long userId) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new NotFoundException(String.format("User with id %d not found", userId));
    }
    userRepository.delete(user.get());
  }


  @Override
  public UserDto create(CreateUserDto createUserDto) {
    User user = UserMapper.toUser(createUserDto);
    return UserMapper.toUserDto(saveOrUpdate(user));
  }


  public User saveOrUpdate(User saveUser) {
    if (saveUser.getId() == null) {
      return userRepository.save(saveUser);
    }

    Optional<User> user = userRepository.findById(saveUser.getId());

    if (user.isEmpty()) {
      throw new NotFoundException("User not found");
    }

    return userRepository.save(saveUser);
  }

  @Override
  public UserDto update(UpdateUserDto updateUserDto) {
    User user = UserMapper.toUser(updateUserDto);
    return UserMapper.toUserDto(saveOrUpdate(user));
  }
}
