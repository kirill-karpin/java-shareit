package ru.practicum.shareit.item.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;

  private final UserRepository userRepository;

  public ItemServiceImpl(ItemRepository itemRepository, UserRepository userService) {
    this.itemRepository = itemRepository;
    this.userRepository = userService;
  }

  @Override
  public ItemDto create(Long userId, CreateItemDto createItemDto) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("user not found"));

    Item item = ItemMapper.toItem(createItemDto);
    item.setOwner(user);

    return ItemMapper.toItemDto(createOrUpdate(item));
  }

  @Override
  public ItemDto getById(Long id) {
    if (id == null) {
      throw new NotFoundException("item id is null");
    }

    if (id <= 0) {
      throw new NotFoundException("item id is negative");
    }

    return ItemMapper.toItemDto(
        itemRepository.findById(id).orElseThrow(() -> new NotFoundException("item not found")));

  }

  @Override
  public List<ItemDto> getAllByUserId(Long userId) {
    userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("user not found"));

    return itemRepository.findAllByOwner_Id(userId).stream()
        .map(ItemMapper::toItemDto).collect(Collectors.toList());
  }

  @Override
  public ItemDto update(UpdateItemDto updateItemDto) {
    return ItemMapper.toItemDto(createOrUpdate(ItemMapper.toItem(updateItemDto)));
  }

  public ItemDto updateItemByIdWithOwnerId(Long userId, Long itemId, UpdateItemDto updateItemDto) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("user not found"));

    if (user == null) {
      throw new NotFoundException("user not found");
    }

    Optional<Item> item = itemRepository.findById(itemId);

    if (item.isEmpty()) {
      throw new NotFoundException("item not found");
    }

    if (!item.get().getOwner().getId().equals(userId)) {
      throw new NotFoundException("user not owner of this item");
    }

    if (updateItemDto.getAvailable() != null) {
      item.get().setIsAvailable(updateItemDto.getAvailable());
    }
    if (updateItemDto.getDescription() != null) {
      item.get().setDescription(updateItemDto.getDescription());
    }
    if (updateItemDto.getName() != null) {
      item.get().setName(updateItemDto.getName());
    }

    return ItemMapper.toItemDto(createOrUpdate(item.orElse(null)));
  }

  @Override
  public List<ItemDto> search(String searchString) {
    if (searchString.isEmpty()) {
      return List.of();
    }

    return itemRepository.findAllByNameContainingIgnoreCaseAndIsAvailable(searchString, true)
        .stream()
        .map(ItemMapper::toItemDto).collect(Collectors.toList());
  }


  @Override
  public ItemDto createItemWithOwnerId(CreateItemDto itemDto, Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("user not found"));

    if (user == null) {
      throw new NotFoundException("user not found");
    }
    Item item = ItemMapper.toItem(itemDto);

    item.setOwner(user);

    return ItemMapper.toItemDto(createOrUpdate(item));
  }

  @Override
  public ItemDto getItemByIdAndOwnerId(Long itemId, Long userId) {
    userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("user not found"));

    return ItemMapper.toItemDto(
        itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("item not found")));
  }

  private Item createOrUpdate(Item item) {
    if (item.getId() == null) {
      return itemRepository.save(item);
    }
    Optional<Item> existingItem = itemRepository.findById(item.getId());
    if (existingItem.isEmpty()) {
      throw new NotFoundException("item not found");
    }

    return itemRepository.save(item);
  }
}
