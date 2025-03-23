package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.repository.BaseCrudRepository;
import ru.practicum.shareit.storage.StoreManager;
import ru.practicum.shareit.user.dto.UserDto;

@Repository
public class UserRepositoryImpl extends BaseCrudRepository<UserDto> implements UserRepository {

  public UserRepositoryImpl(StoreManager<UserDto> store) {
    super(store);
  }

  public UserDto findByEmail(String email) {
    for (UserDto user : findAll()) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    return null;
  }

  @Override
  public UserDto save(UserDto entity) {
    UserDto existingUser = findByEmail(entity.getEmail());

    if ((existingUser != null) && !existingUser.getId().equals(entity.getId())) {
      throw new ValidationException(
          "User with email " + entity.getEmail() + " already exists");
    }
    return super.save(entity);
  }
}
