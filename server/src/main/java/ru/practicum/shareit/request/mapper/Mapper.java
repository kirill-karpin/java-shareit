package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.UpdateItemRequestDto;

public class Mapper {

  public static ItemRequest toItemRequest(CreateItemRequestDto createItemRequestDto) {
    return ItemRequest.builder()
        .description(createItemRequestDto.getDescription())
        .requestor(createItemRequestDto.getRequestor())
        .build();

  }

  public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
    return ItemRequestDto.builder()
        .id(itemRequest.getId())
        .description(itemRequest.getDescription())
        .requestor(itemRequest.getRequestor())
        .build();
  }

  public static void merge(ItemRequest itemRequest, UpdateItemRequestDto updateItemRequestDto) {
    if (updateItemRequestDto.getDescription() != null) {
      itemRequest.setDescription(updateItemRequestDto.getDescription());
    }
    if (updateItemRequestDto.getRequestor().getId() != null) {
      itemRequest.setRequestor(updateItemRequestDto.getRequestor());
    }
  }
}
