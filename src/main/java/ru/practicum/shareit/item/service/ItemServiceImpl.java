package ru.practicum.shareit.item.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

@Service
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;

  private final UserService userService;

  public ItemServiceImpl(ItemRepository itemRepository, UserService userService) {
    this.itemRepository = itemRepository;
    this.userService = userService;
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
    User user = userService.getById(userId);

    if (user == null) {
      throw new NotFoundException("user not found");
    }

    return StreamSupport.stream(itemRepository.findAll().spliterator(), false)
        .filter(i -> i.getOwnerId().equals(userId)).collect(Collectors.toList());
  }

  @Override
  public Item update(Item item) {
    return itemRepository.save(item);
  }

  public ItemDto updateItemByIdWithOwnerId(Long userId, Long itemId, UpdateItemDto updateItemDto) {

    User user = userService.getById(userId);

    if (user == null) {
      throw new NotFoundException("user not found");
    }

    Item item = getById(itemId);
    if (item == null) {
      throw new NotFoundException("item not found");
    }

    if (!item.getOwnerId().equals(userId)) {
      throw new NotFoundException("user not owner of this item");
    }

    if (updateItemDto.getAvailable() != null) {
      item.setAvailable(updateItemDto.getAvailable());
    }
    if (updateItemDto.getDescription() != null) {
      item.setDescription(updateItemDto.getDescription());
    }
    if (updateItemDto.getName() != null) {
      item.setName(updateItemDto.getName());
    }

    return ItemMapper.toItemDto(update(item));
  }

  @Override
  public List<Item> search(String searchString) {
    if (searchString.isEmpty()) {
      return List.of();
    }

    return StreamSupport.stream(itemRepository.findAll().spliterator(), false).filter(
            i -> i.getName().toLowerCase().contains(searchString.toLowerCase()) && i.getAvailable())
        .collect(Collectors.toList());
  }


  @Override
  public ItemDto createItemWithOwnerId(ItemDto itemDto, Long userId) {
    User user = userService.getById(userId);

    if (user == null) {
      throw new NotFoundException("user not found");
    }
    Item item = ItemMapper.toItem(itemDto);

    item.setOwnerId(userId);

    return ItemMapper.toItemDto(create(item));
  }

  @Override
  public Item getItemByIdAndOwnerId(Long itemId, Long userId) {
    User user = userService.getById(userId);

    if (user == null) {
      throw new NotFoundException("user not found");
    }

    return getById(itemId);
  }
}
