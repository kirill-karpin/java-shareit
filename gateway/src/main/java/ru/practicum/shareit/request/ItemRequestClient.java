package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;

@Service
public class ItemRequestClient extends BaseClient {

  private static final String API_PREFIX = "/requests";

  public ItemRequestClient(@Value("${shareit-server.url}") String serverUrl,
      RestTemplateBuilder builder) {
    super(
        builder
            .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
            .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
            .build()
    );
  }

  public ResponseEntity<?> createRequest(Long userId, CreateItemRequestDto createItemRequestDto) {
    return post("", userId, createItemRequestDto);
  }

  public ResponseEntity<?> getUserItemRequests(Long userId) {
    return get("", userId);
  }

  public ResponseEntity<?> getById(Long userId, Long requestId) {
    return get("/" + requestId, userId);
  }
}
