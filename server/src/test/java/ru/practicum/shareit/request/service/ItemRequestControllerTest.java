package ru.practicum.shareit.request.service;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;

@WebMvcTest(controllers = ItemRequestController.class)
public class ItemRequestControllerTest {

  @Autowired
  ObjectMapper mapper;

  @MockBean
  ItemRequestService requestService;

  @Autowired
  private MockMvc mvc;

  private ItemRequestDto requestDto;

  @BeforeEach
  void setUp() {
    requestDto = new ItemRequestDto();
    requestDto.setId(1L);
    requestDto.setDescription("description");
    requestDto.setRequestor(new User(1L, "test", "test@test.ru"));
    requestDto.setCreated(LocalDateTime.now());

  }

  @SneakyThrows
  @Test
  void createRequestTest() {
    when(requestService.create(anyLong(), any(CreateItemRequestDto.class)))
        .thenReturn(requestDto);

    mvc.perform(post("/requests")
            .header("X-Sharer-User-Id", 1)
            .content(mapper.writeValueAsString(requestDto))
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(requestDto.getId()), Long.class))
        .andExpect(jsonPath("$.description", is(requestDto.getDescription())));

    verify(requestService, times(1))
        .create(anyLong(), any(CreateItemRequestDto.class));
  }

  @SneakyThrows
  @Test
  void getAllRequestsByUserTest() {
    List<ItemRequestDto> requests = List.of(requestDto);

    when(requestService.getUserRequests(anyLong())).thenReturn(requests);

    mvc.perform(get("/requests")
            .header("X-Sharer-User-Id", 1)
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(requests.size())))
        .andExpect(jsonPath("$[0].id", is(requestDto.getId()), Long.class))
        .andExpect(jsonPath("$[0].description", is(requestDto.getDescription())));

    verify(requestService, times(1)).getUserRequests(anyLong());
  }

  @SneakyThrows
  @Test
  void getRequestByIdTest() {
    Long requestId = requestDto.getId();

    when(requestService.getById(anyLong())).thenReturn(requestDto);

    mvc.perform(get("/requests/{requestId}", requestId)
            .content(mapper.writeValueAsString(requestDto))
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(requestDto.getId()), Long.class))
        .andExpect(jsonPath("$.description", is(requestDto.getDescription())));

    verify(requestService, times(1)).getById(requestId);
  }
}
