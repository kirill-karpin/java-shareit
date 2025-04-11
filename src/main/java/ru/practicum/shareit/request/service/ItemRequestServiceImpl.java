package ru.practicum.shareit.request.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.UpdateItemRequestDto;
import ru.practicum.shareit.request.mapper.Mapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;

@Service
public class ItemRequestServiceImpl {

  private ItemRequestRepository itemRequestRepository;

  public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository) {
    this.itemRequestRepository = itemRequestRepository;
  }


  public ItemRequest saveOrUpdateItemRequest(ItemRequest itemRequest) {
    if (itemRequest.getId() == null) {
      return itemRequestRepository.save(itemRequest);
    }

    Optional<ItemRequest> user = itemRequestRepository.findById(itemRequest.getId());

    if (user.isEmpty()) {
      throw new NotFoundException("ItemRequest not found");
    }

    return itemRequestRepository.save(itemRequest);
  }

  public ItemRequestDto create(CreateItemRequestDto createItemDto) {
    return Mapper.toItemRequestDto(saveOrUpdateItemRequest(Mapper.toItemRequest(createItemDto)));
  }

  public ItemRequestDto update(UpdateItemRequestDto updateItemRequestDto) {
    if (updateItemRequestDto.getId() == null) {
      throw new NotFoundException("ItemRequest not found");
    }
    Optional<ItemRequest> itemRequest = itemRequestRepository.findById(
        updateItemRequestDto.getId());

    if (itemRequest.isEmpty()) {
      throw new NotFoundException(
          "ItemRequest with id " + updateItemRequestDto.getId() + " not found"
      );
    }

    Mapper.merge(itemRequest.get(), updateItemRequestDto);

    return Mapper.toItemRequestDto(saveOrUpdateItemRequest(itemRequest.get()));
  }
}
