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
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

  private static final String USER_ID_HEADER = "X-Sharer-User-Id";
  private final ItemService itemService;
  private final UserService userService;

  public ItemController(ItemService itemService, UserService userService) {
    this.itemService = itemService;
    this.userService = userService;
  }


  @PostMapping
  public ItemDto createItem(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestBody @Valid ItemDto itemDto) {

    return itemService.createItemWithOwnerId(itemDto, userId);
  }

  @GetMapping("/{itemId}")
  public Item getItemById(@RequestHeader(USER_ID_HEADER) Long userId,
      @PathVariable Long itemId) {

    return itemService.getItemByIdAndOwnerId(itemId, userId);
  }

  @GetMapping
  public List<Item> getItemsById(@RequestHeader(USER_ID_HEADER) Long userId) {

    return itemService.getAllByUserId(userId);
  }

  @PatchMapping("/{itemId}")
  public ItemDto updateItem(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestBody @Valid UpdateItemDto updateItemDto,
      @PathVariable Long itemId) {
    return itemService.updateItemByIdWithOwnerId(userId, itemId, updateItemDto);
  }

  @GetMapping("/search")
  public List<Item> searchItems(@RequestParam("text") String searchString) {
    return itemService.search(searchString);
  }

}
