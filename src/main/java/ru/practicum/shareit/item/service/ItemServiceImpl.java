package ru.practicum.shareit.item.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {

  ItemRepository itemRepository;

  public ItemServiceImpl(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @Override
  public Item create(Item item) {
    return itemRepository.save(item);
  }

  @Override
  public Item getById(Long id) {
    return itemRepository.findById(id);
  }

  @Override
  public List<Item> getAllByUserId(Long userId) {
    return StreamSupport.stream(itemRepository.findAll().spliterator(), false)
        .filter(i -> i.getOwnerId().equals(userId))
        .collect(Collectors.toList());
  }

  @Override
  public Item update(Item item) {
    return itemRepository.save(item);
  }

  @Override
  public List<Item> search(String searchString) {
    if (searchString.isEmpty()) {
      return List.of();
    }

    return StreamSupport.stream(itemRepository.findAll().spliterator(), false)
        .filter(i -> i.getName().toLowerCase().contains(searchString.toLowerCase())
            && i.getAvailable())
        .collect(Collectors.toList());
  }
}
