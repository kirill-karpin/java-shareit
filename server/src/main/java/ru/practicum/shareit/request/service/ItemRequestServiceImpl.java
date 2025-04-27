package ru.practicum.shareit.request.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.UpdateItemRequestDto;
import ru.practicum.shareit.request.mapper.Mapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {

  private final ItemRequestRepository itemRequestRepository;
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;

  public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository,
      UserRepository userRepository, ItemRepository itemRepository) {
    this.itemRequestRepository = itemRequestRepository;
    this.userRepository = userRepository;
    this.itemRepository = itemRepository;
  }


  @Override
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

  @Override
  public ItemRequestDto create(Long userId, CreateItemRequestDto createItemDto) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));

    ItemRequest saveOrUpdateItemRequest = Mapper.toItemRequest(createItemDto);
    saveOrUpdateItemRequest.setRequestor(user);

    return Mapper.toItemRequestDto(saveOrUpdateItemRequest(saveOrUpdateItemRequest));
  }

  @Override
  public ItemRequestDto update(Long userId, UpdateItemRequestDto updateItemRequestDto) {
    if (userId == null) {
      throw new NotFoundException("ItemRequest not found");
    }
    ItemRequest itemRequest = itemRequestRepository.findById(
            userId)
        .orElseThrow(() -> new NotFoundException("ItemRequest with id " + userId + " not found"));

    Mapper.merge(itemRequest, updateItemRequestDto);

    return Mapper.toItemRequestDto(saveOrUpdateItemRequest(itemRequest));
  }

  @Override
  public List<ItemRequestDto> getUserRequests(Long userId) {

    return itemRequestRepository.findAllByRequestor_Id(userId).stream()
        .map(Mapper::toItemRequestDto).toList();
  }

  @Override
  public List<ItemRequestDto> getAll() {
    return itemRequestRepository.findAll().stream()
        .map(Mapper::toItemRequestDto).toList();
  }

  @Override
  public ItemRequestDto getById(Long requestId) {
    var itemRequest = Mapper.toItemRequestDto(itemRequestRepository.findById(requestId)
        .orElseThrow(() -> new NotFoundException("Request with id " + requestId + " not found")));

    List<ItemDto> items = itemRepository.findAllByRequest_Id(requestId).stream()
        .map(ItemMapper::toItemDto).toList();

    itemRequest.setItems(items);

    return itemRequest;
  }
}
