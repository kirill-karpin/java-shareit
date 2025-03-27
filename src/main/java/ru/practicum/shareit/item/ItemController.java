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
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

  private final ItemService itemService;
  private final UserService userService;

  public ItemController(ItemService itemService, UserService userService) {
    this.itemService = itemService;
    this.userService = userService;
  }


  @PostMapping
  public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
      @RequestBody @Valid ItemDto itemDto) {
    User user = userService.getById(userId);

    if (user == null) {
      throw new NotFoundException("user not found");
    }
    Item item = ItemMapper.toItem(itemDto);

    item.setOwnerId(userId);

    return ItemMapper.toItemDto(itemService.create(item));
  }

  @GetMapping("/{itemId}")
  public Item getItemById(@RequestHeader("X-Sharer-User-Id") Long userId,
      @PathVariable Long itemId) {
    User user = userService.getById(userId);

    if (user == null) {
      throw new NotFoundException("user not found");
    }

    return itemService.getById(itemId);
  }

  @GetMapping
  public List<Item> getItemById(@RequestHeader("X-Sharer-User-Id") Long userId) {
    User user = userService.getById(userId);

    if (user == null) {
      throw new NotFoundException("user not found");
    }

    return itemService.getAllByUserId(userId);
  }

  @PatchMapping("/{itemId}")
  public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
      @RequestBody @Valid UpdateItemDto updateItemDto,
      @PathVariable Long itemId) {
    User user = userService.getById(userId);

    if (user == null) {
      throw new NotFoundException("user not found");
    }

    Item item = itemService.getById(itemId);
    if (item == null) {
      throw new NotFoundException("item not found");
    }

    if (!item.getOwnerId().equals(userId)) {
      throw new NotFoundException("user not found");
    }

    if (updateItemDto.getAvailable() != null) {
      item.setAvailable(updateItemDto.getAvailable());
    }
    if (updateItemDto.getDescription() != null) {
      item.setDescription(updateItemDto.getDescription());
    }
    if (updateItemDto.getName() != null) {
      item.setName(updateItemDto.getName());
    }

    return ItemMapper.toItemDto(itemService.update(item));
  }

  @GetMapping("/search")
  public List<Item> searchItems(@RequestParam("text") String searchString) {
    return itemService.search(searchString);
  }

}
