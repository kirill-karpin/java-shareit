package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.repository.BaseCrudRepository;
import ru.practicum.shareit.storage.StorageManager;

@Repository

public class ItemRepositoryImpl extends BaseCrudRepository<Item> implements ItemRepository {

  public ItemRepositoryImpl() {
    super(StorageManager.getStore(Item.class));
  }
}
