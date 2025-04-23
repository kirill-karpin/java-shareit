package ru.practicum.shareit.item.service;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

  @Autowired
  ObjectMapper mapper;

  @MockBean
  ItemService itemService;

  @MockBean
  CommentService commentService;

  @Autowired
  private MockMvc mvc;

  private ItemDto itemDto;

  @BeforeEach
  void setUp() {
    itemDto = ItemDto.builder()
        .id(1L)
        .name("test")
        .description("test")
        .available(true)
        .build();
  }

  @SneakyThrows
  @Test
  void createItem() {
    when(itemService.create(anyLong(), any(CreateItemDto.class)))
        .thenReturn(itemDto);

    mvc.perform(post("/items")
            .header("X-Sharer-User-Id", 1)
            .content(mapper.writeValueAsString(itemDto))
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
        .andExpect(jsonPath("$.description", is(itemDto.getDescription())));

    verify(itemService, times(1))
        .create(anyLong(), any(CreateItemDto.class));
  }

  @SneakyThrows
  @Test
  void createOnRequest() {
    when(itemService.create(anyLong(), any(CreateItemDto.class)))
        .thenReturn(itemDto);

    mvc.perform(post("/items")
            .header("X-Sharer-User-Id", 1)
            .content(mapper.writeValueAsString(itemDto))
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
        .andExpect(jsonPath("$.description", is(itemDto.getDescription())));

    verify(itemService, times(1))
        .create(anyLong(), any(CreateItemDto.class));

  }

  @SneakyThrows
  @Test
  void createOnRequestWithoutDescription() {

  }
}
