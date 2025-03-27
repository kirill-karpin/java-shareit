package ru.practicum.shareit.item.service;

import java.util.List;
import ru.practicum.shareit.item.model.Item;

public interface ItemService {

  Item create(Item item);

  Item getById(Long id);

  List<Item> getAllByUserId(Long userId);

  Item update(Item item);

  List<Item> search(String searchString);
}
