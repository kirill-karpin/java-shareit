package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

public class ItemMapper {

  public static ItemDto toItemDto(Item item) {
    return ItemDto.builder()
        .id(item.getId())
        .name(item.getName())
        .description(item.getDescription())
        .available(item.getIsAvailable()).build();
  }

  public static Item toItem(ItemDto itemDto) {
    return Item.builder()
        .id(itemDto.getId())
        .name(itemDto.getName())
        .description(itemDto.getDescription())
        .isAvailable(itemDto.getAvailable()).build();
  }

  public static Item toItem(CreateItemDto itemDto) {
    return Item.builder()
        .name(itemDto.getName())
        .description(itemDto.getDescription())
        .isAvailable(itemDto.getAvailable()).build();
  }

  public static Item toItem(UpdateItemDto itemDto) {
    return Item.builder()
        .id(itemDto.getId())
        .name(itemDto.getName())
        .description(itemDto.getDescription())
        .isAvailable(itemDto.getAvailable()).build();
  }

  public static void merge(Item item, UpdateItemDto itemDto) {
    if (itemDto.getName() != null) {
      item.setName(itemDto.getName());
    }
    if (itemDto.getDescription() != null) {
      item.setDescription(itemDto.getDescription());
    }

    if (itemDto.getAvailable() != null) {
      item.setIsAvailable(itemDto.getAvailable());
    }
  }
}
