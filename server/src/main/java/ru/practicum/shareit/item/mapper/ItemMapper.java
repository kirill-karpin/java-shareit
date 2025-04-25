package ru.practicum.shareit.item.mapper;

import java.util.LinkedHashSet;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;

public class ItemMapper {

  public static ItemDto toItemDto(Item item) {
    return ItemDto.builder().id(item.getId()).name(item.getName())
        .description(item.getDescription())
        .comments(item.getComments().stream().map(CommentMapper::toCommentDto).toList())
        .available(item.getIsAvailable()).build();
  }

  public static Item toItem(CreateItemDto itemDto) {
    return Item.builder()
        .name(itemDto.getName()).description(itemDto.getDescription())
        .comments(new LinkedHashSet<>())
        .isAvailable(itemDto.getAvailable()).build();
  }


}
