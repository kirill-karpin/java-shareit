package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.repository.CrudRepository;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserRepository extends CrudRepository<UserDto, Long> {

}
