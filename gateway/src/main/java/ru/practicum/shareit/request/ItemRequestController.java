package ru.practicum.shareit.request;

import static ru.practicum.shareit.Config.USER_ID_HEADER;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Slf4j
@Validated
public class ItemRequestController {

  private final ItemRequestClient client;

  public ItemRequestController(ItemRequestClient client) {
    this.client = client;
  }

  @PostMapping("/")
  public ResponseEntity<?> createRequest(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestBody CreateItemRequestDto createItemRequestDto) {
    log.info("Запрос на создание запроса аренды вещи");
    return client.createRequest(userId, createItemRequestDto);
  }

  @GetMapping("/")
  public ResponseEntity<?> getUserItemRequests(@RequestHeader(USER_ID_HEADER) Long userId) {
    log.info("Получение запросов аренды вещей пользователя");
    return client.getUserItemRequests(userId);
  }

  @GetMapping("/{requestId}")
  public ResponseEntity<?> getById(@RequestHeader(USER_ID_HEADER) Long userId, Long requestId) {
    log.info("Получение запроса аренды вещей пользователя по id");
    return client.getById(userId, requestId);
  }
}
