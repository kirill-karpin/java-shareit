package ru.practicum.shareit.item;

import static ru.practicum.shareit.Config.USER_ID_HEADER;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Slf4j
@Validated
public class ItemController {


  private final ItemClient itemClient;

  public ItemController(ItemClient itemClient) {
    this.itemClient = itemClient;
  }


  @PostMapping
  public ResponseEntity<?> createItem(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestBody @Valid CreateItemDto itemDto) {
    log.info("Получен запрос на создание вещи {} пользователем с идентификатором {}", itemDto,
        userId);
    return itemClient.create(userId, itemDto);
  }

  @GetMapping("/{itemId}")
  public ResponseEntity<?> getItemByIdAndOwnerId(@RequestHeader(USER_ID_HEADER) Long userId,
      @PathVariable Long itemId) {
    log.info("Получен запрос на получение вещи с идентификатором {}", itemId);
    return itemClient.getItemByIdAndOwnerId(itemId, userId);
  }

  @GetMapping
  public ResponseEntity<?> getItemsById(@RequestHeader(USER_ID_HEADER) Long userId) {
    log.info("Получен запрос на получение вещей пользователя с идентификатором {}", userId);
    return itemClient.getAllByUserId(userId);
  }

  @PatchMapping("/{itemId}")
  public ResponseEntity<?> updateItem(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestBody @Valid UpdateItemDto updateItemDto,
      @PathVariable Long itemId) {
    log.info("Получен запрос на обновление вещи с идентификатором {}", itemId);
    return itemClient.updateItemByIdWithOwnerId(userId, itemId, updateItemDto);
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchItems(@RequestParam("text") String searchString) {
    log.info("Получен запрос на поиск вещи с текстом {}", searchString);
    return itemClient.search(searchString);

  }

  @PostMapping("/{itemId}/comment")
  public ResponseEntity<?> addComment(@RequestHeader(USER_ID_HEADER) Long userId,
      @PathVariable Long itemId,
      @RequestBody @Valid CreateCommentDto commentDto) {
    return itemClient.commentBookingPast(userId, itemId, commentDto);
  }
}
