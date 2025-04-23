package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

  private static final String USER_ID_HEADER = "X-Sharer-User-Id";

  private final ItemClient itemClient;

  public ItemController(ItemClient itemClient) {
    this.itemClient = itemClient;
  }


  @PostMapping
  public ResponseEntity<Object> createItem(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestBody @Valid CreateItemDto itemDto) {

    return itemClient.create(userId, itemDto);
  }

  @GetMapping("/{itemId}")
  public ResponseEntity<Object> getItemByIdAndOwnerId(@RequestHeader(USER_ID_HEADER) Long userId,
      @PathVariable Long itemId) {

    return itemClient.getItemByIdAndOwnerId(itemId, userId);
  }

  @GetMapping
  public ResponseEntity<Object> getItemsById(@RequestHeader(USER_ID_HEADER) Long userId) {
    return itemService.getAllByUserId(userId);
  }

  @PatchMapping("/{itemId}")
  public ResponseEntity<Object> updateItem(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestBody @Valid UpdateItemDto updateItemDto,
      @PathVariable Long itemId) {
    return itemClient.updateItemByIdWithOwnerId(userId, itemId, updateItemDto);
  }

  @GetMapping("/search")
  public ResponseEntity<Object> searchItems(@RequestParam("text") String searchString) {
    return itemClient.search(searchString);

  }

  @PostMapping("/{itemId}/comment")
  public CommentDto addComment(@RequestHeader(USER_ID_HEADER) Long userId,
      @PathVariable Long itemId,
      @RequestBody @Valid CreateCommentDto commentDto) {
    return itemClient.commentBookingPast(userId, itemId, commentDto);
  }
}
