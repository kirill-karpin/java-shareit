package ru.practicum.shareit.storage;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

class StoreManagerTest {

  @Test
  void getStore() {

    Storage<ItemDto> store = StorageManager.getStore(ItemDto.class);
    Storage<UserDto> store2 = StorageManager.getStore(UserDto.class);
    assertNotNull(store);
    assertNotNull(store2);

  }
}
