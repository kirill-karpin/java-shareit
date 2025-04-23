package ru.practicum.shareit.request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

  private final ItemRequestClient client;

  public ItemRequestController(ItemRequestClient client) {
    this.client = client;
  }

  @PostMapping("/")
  public ResponseEntity<?> createRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
      @RequestBody CreateItemRequestDto createItemRequestDto) {
    return client.createRequest(userId, createItemRequestDto);
  }

  @GetMapping("/")
  public ResponseEntity<?> getUserItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
    return client.getUserItemRequests(userId);
  }

  @GetMapping("/{requestId}")
  public ResponseEntity<?> getById(@RequestHeader("X-Sharer-User-Id") Long userId, Long requestId) {
    return client.getById(userId, requestId);
  }
}
