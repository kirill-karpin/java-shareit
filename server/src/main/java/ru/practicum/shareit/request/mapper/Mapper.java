package ru.practicum.shareit.request.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.UpdateItemRequestDto;

public class Mapper {

  public static ItemRequest toItemRequest(CreateItemRequestDto createItemRequestDto) {
    return ItemRequest.builder()
        .description(createItemRequestDto.getDescription())
        .created(Instant.now())
        .build();

  }

  public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
    return ItemRequestDto.builder()
        .id(itemRequest.getId())
        .description(itemRequest.getDescription())
        .requestor(itemRequest.getRequestor())
        .created(LocalDateTime.ofInstant(itemRequest.getCreated(), ZoneOffset.ofHours(0)))
        .items(new ArrayList<>())
        .build();
  }

  public static void merge(ItemRequest itemRequest, UpdateItemRequestDto updateItemRequestDto) {
    itemRequest.setDescription(
        (updateItemRequestDto.getDescription() != null ? updateItemRequestDto.getDescription()
            : itemRequest.getDescription()));
  }
}
