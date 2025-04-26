package ru.practicum.shareit.item.service;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private ItemService itemService;

  @MockBean
  private CommentService commentService;

  @Autowired
  private MockMvc mvc;

  private ItemDto itemDto;
  private CommentDto commentDto;

  @BeforeEach
  void setUp() {
    itemDto = ItemDto.builder()
        .id(1L)
        .name("test")
        .description("test")
        .available(true)
        .build();

    commentDto = CommentDto.builder()
        .id(1L)
        .text("test")
        .authorName("test")
        .created(LocalDateTime.now())
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
  void getItemById() {

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
  void getItemsById() {
    Long userId = 1L;
    List<ItemDto> items = List.of(itemDto);

    when(itemService.getAllByUserId(userId))
        .thenReturn(List.of(itemDto));

    mvc.perform(get("/items")
            .header("X-Sharer-User-Id", userId)
            .content(mapper.writeValueAsString(itemDto))
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(items.size())))
        .andExpect(jsonPath("$[0].id", is(itemDto.getId()), Long.class))
        .andExpect(jsonPath("$[0].description", is(itemDto.getDescription())));

    verify(itemService, times(1))
        .getAllByUserId(userId);
  }

  @SneakyThrows
  @Test
  void updateItem() {
    Long userId = 1L;
    Long itemId = 1L;

    UpdateItemDto updateItemDto = UpdateItemDto.builder()
        .id(1L)
        .name("new-test")
        .description("new-test")
        .available(false)
        .build();

    when(itemService.updateItemByIdWithOwnerId(userId, itemId, updateItemDto))
        .thenReturn(itemDto);

    mvc.perform(patch("/items/{id}", itemId)
            .header("X-Sharer-User-Id", 1)
            .content(mapper.writeValueAsString(updateItemDto))
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class));

    verify(itemService, times(1))
        .updateItemByIdWithOwnerId(userId, itemId, updateItemDto);
  }

  @SneakyThrows
  @Test
  void updateItemWithInvalidUserId() {
    Long userId = 1L;
    Long itemId = 1L;

    UpdateItemDto updateItemDto = UpdateItemDto.builder()
        .id(1L)
        .name("new-test")
        .description("new-test")
        .available(false)
        .build();

    when(itemService.updateItemByIdWithOwnerId(userId, itemId, updateItemDto))
        .thenThrow(new NotFoundException("user not found"));

    mvc.perform(patch("/items/{id}", itemId)
            .header("X-Sharer-User-Id", userId)
            .content(mapper.writeValueAsString(updateItemDto))
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(itemService, times(1))
        .updateItemByIdWithOwnerId(userId, itemId, updateItemDto);
  }

  @SneakyThrows
  @Test
  void searchItems() {
    String text = "test";
    Long userId = 1L;

    List<ItemDto> itemDtoList = List.of(itemDto);
    when(itemService.search(text)).thenReturn(itemDtoList);
    mvc.perform(
            get("/items/search")
                .header("X-Sharer-User-Id", userId)
                .param("text", text))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(itemDtoList)));
    verify(itemService, times(1)).search(text);
  }

  @SneakyThrows
  @Test
  void addComment() {
    Long userId = 1L;
    Long itemId = 1L;
    CreateCommentDto newCommentRequest = new CreateCommentDto();
    newCommentRequest.setText("test");

    when(commentService.commentBookingPast(userId, itemId, newCommentRequest)).thenReturn(
        commentDto);

    mvc.perform(
            post("/items/{itemId}/comment", itemId)
                .header("X-Sharer-User-Id", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newCommentRequest)))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(commentDto)));
    verify(commentService, times(1)).commentBookingPast(itemId, userId, newCommentRequest);
  }
}
