package ru.practicum.shareit.request;

import static ru.practicum.shareit.Config.USER_ID_HEADER;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

  private final ItemRequestService itemRequestClient;

  public ItemRequestController(ItemRequestService itemRequestClient) {
    this.itemRequestClient = itemRequestClient;
  }


  @PostMapping
  public ItemRequestDto post(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestBody CreateItemRequestDto createItemRequestDto) {
    return itemRequestClient.create(userId, createItemRequestDto);
  }

  @GetMapping
  public List<ItemRequestDto> getUserRequests(@RequestHeader(USER_ID_HEADER) Long userId) {
    return itemRequestClient.getUserRequests(userId);
  }

  @GetMapping("/{requestId}")
  public ItemRequestDto getById(@PathVariable Long requestId) {
    return itemRequestClient.getById(requestId);
  }
}
