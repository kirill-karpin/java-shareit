package ru.practicum.shareit.request.service;

import java.util.List;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.UpdateItemRequestDto;

public interface ItemRequestService {

  ItemRequest saveOrUpdateItemRequest(ItemRequest itemRequest);

  ItemRequestDto create(Long userId, CreateItemRequestDto createItemDto);

  ItemRequestDto update(Long userId, UpdateItemRequestDto updateItemRequestDto);

  List<ItemRequestDto> getUserRequests(Long userId);

  List<ItemRequestDto> getAll();

  ItemRequestDto getById(Long requestId);


}
