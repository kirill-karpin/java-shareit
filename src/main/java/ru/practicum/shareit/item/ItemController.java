package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import java.util.List;
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
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.service.CommentService;
import ru.practicum.shareit.item.service.ItemService;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

  private static final String USER_ID_HEADER = "X-Sharer-User-Id";
  private final ItemService itemService;
  private final CommentService commentService;

  public ItemController(ItemService itemService, CommentService commentService) {
    this.itemService = itemService;
    this.commentService = commentService;
  }


  @PostMapping
  public ItemDto createItem(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestBody @Valid CreateItemDto itemDto) {

    return itemService.create(userId, itemDto);
  }

  @GetMapping("/{itemId}")
  public ItemDto getItemById(@RequestHeader(USER_ID_HEADER) Long userId,
      @PathVariable Long itemId) {

    return itemService.getItemByIdAndOwnerId(itemId, userId);
  }

  @GetMapping
  public List<ItemDto> getItemsById(@RequestHeader(USER_ID_HEADER) Long userId) {
    return itemService.getAllByUserId(userId);
  }

  @PatchMapping("/{itemId}")
  public ItemDto updateItem(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestBody @Valid UpdateItemDto updateItemDto,
      @PathVariable Long itemId) {
    return itemService.updateItemByIdWithOwnerId(userId, itemId, updateItemDto);
  }

  @GetMapping("/search")
  public List<ItemDto> searchItems(@RequestParam("text") String searchString) {
    return itemService.search(searchString);

  }

  @PostMapping("/{itemId}/comment")
  public CommentDto addComment(@RequestHeader(USER_ID_HEADER) Long userId,
      @PathVariable Long itemId,
      @RequestBody @Valid CreateCommentDto commentDto) {
    return commentService.commentBookingPast(userId, itemId, commentDto);
  }
}
