package ru.practicum.shareit.item.service;

import java.util.List;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;


public interface ItemService {

  ItemDto create(Long userId, CreateItemDto item);

  ItemDto getById(Long id);

  List<Item> getAllByUserId(Long userId);

  ItemDto update(UpdateItemDto updateItemDto);

  List<Item> search(String searchString);

  ItemDto updateItemByIdWithOwnerId(Long userId, Long itemId, UpdateItemDto item);

  ItemDto createItemWithOwnerId(CreateItemDto item, Long userId);

  ItemDto getItemByIdAndOwnerId(Long itemId, Long userId);
}
