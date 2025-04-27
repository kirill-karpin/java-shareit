package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

@Service
public class ItemClient extends BaseClient {

  private static final String API_PREFIX = "/items";

  public ItemClient(@Value("${shareit-server.url}") String serverUrl,
      RestTemplateBuilder builder) {
    super(
        builder
            .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
            .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
            .build()
    );
  }

  public ResponseEntity<?> create(Long userId, @Valid CreateItemDto itemDto) {
    return post("", userId, itemDto);
  }

  public ResponseEntity<?> getItemByIdAndOwnerId(Long itemId, Long userId) {
    return get("/" + itemId, userId);
  }

  public ResponseEntity<?> updateItemByIdWithOwnerId(Long userId, Long itemId,
      @Valid UpdateItemDto updateItemDto) {
    return patch("/" + itemId, userId, updateItemDto);
  }

  public ResponseEntity<?> commentBookingPast(Long userId, Long itemId,
      @Valid CreateCommentDto commentDto) {
    return post("/" + itemId + "/comment", userId, commentDto);
  }

  public ResponseEntity<?> search(String searchString) {
    return get("/search?text=" + searchString);
  }

  public ResponseEntity<?> getAllByUserId(Long userId) {
    return get("", userId);
  }
}
