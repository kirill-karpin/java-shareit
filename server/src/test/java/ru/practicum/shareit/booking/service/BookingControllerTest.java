package ru.practicum.shareit.booking.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;


@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {

  @MockBean
  private BookingService service;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private MockMvc mvc;

  private BookingDto bookingDto;
  private CreateBookingDto createBookingDto;

  private UserDto user;
  private ItemDto item;


  @BeforeEach
  void setUp() {
    user = new UserDto(1L, "Username", "test@example.com");
    item = new ItemDto(1L, "ItemName", "description", true, null, null, null);

    var start = LocalDateTime.now();
    var end = LocalDateTime.now().plusDays(1);

    bookingDto = BookingDto.builder()
        .id(2L)
        .item(item)
        .booker(user)
        .start(start)
        .end(end)
        .status(BookingStatus.WAITING).build();

    createBookingDto = CreateBookingDto.builder()
        .itemId(1L)
        .start(start)
        .end(end)
        .build();
  }

  @Test
  void addBooking_shouldIsOk() throws Exception {
    when(service.create(anyLong(), any(CreateBookingDto.class)))
        .thenReturn(bookingDto);

    mvc.perform(post("/bookings").header("X-Sharer-User-Id", 1)
            .content(mapper.writeValueAsString(createBookingDto))
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(bookingDto.getId()), Long.class))
        .andExpect(jsonPath("$.start", notNullValue()))
        .andExpect(jsonPath("$.end", notNullValue()))
        .andExpect(jsonPath("$.item.id", is(bookingDto.getItem().getId()), Long.class))
        .andExpect(jsonPath("$.booker.id", is(bookingDto.getBooker().getId()), Long.class))
        .andExpect(jsonPath("$.status", is(bookingDto.getStatus().toString())));
  }

  @Test
  void confirmationBooking_shouldIsOk() throws Exception {
    when(service.approve(anyLong(), anyLong(), anyBoolean())).thenReturn(bookingDto);

    mvc.perform(patch("/bookings/{id}", bookingDto.getId()).header("X-Sharer-User-Id", 1)
            .param("approved", "true").content(mapper.writeValueAsString(bookingDto))
            .characterEncoding(StandardCharsets.UTF_8).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(bookingDto.getId()), long.class))
        .andExpect(jsonPath("$.start", notNullValue()))
        .andExpect(jsonPath("$.end", notNullValue()))
        .andExpect(jsonPath("$.item.id", is(bookingDto.getItem().getId()), Long.class))
        .andExpect(jsonPath("$.booker.id", is(bookingDto.getBooker().getId()), Long.class))
        .andExpect(jsonPath("$.status", is(bookingDto.getStatus().toString())));
  }

  @Test
  void findBookingById_shouldIsOk() throws Exception {
    when(service.getByOwnerIdOrBookerId(anyLong(), anyLong())).thenReturn(bookingDto);

    mvc.perform(get("/bookings/{id}", bookingDto.getId())
            .header("X-Sharer-User-Id", user.getId())
            .content(mapper.writeValueAsString(bookingDto)).characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(bookingDto.getId()), long.class))
        .andExpect(jsonPath("$.start", notNullValue()))
        .andExpect(jsonPath("$.end", notNullValue()))
        .andExpect(jsonPath("$.item.id", is(bookingDto.getItem().getId()), Long.class))
        .andExpect(jsonPath("$.booker.id", is(bookingDto.getBooker().getId()), Long.class))
        .andExpect(jsonPath("$.status", is(bookingDto.getStatus().toString())));
  }

  @Test
  void findBookingItemByUserId_shouldIsOk() throws Exception {
    when(service.getBookingByBookerId(anyLong())).thenReturn(List.of(bookingDto));

    mvc.perform(get("/bookings").header("X-Sharer-User-Id", user.getId())
            .content(mapper.writeValueAsString(bookingDto)).characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(bookingDto.getId()), Long.class))
        .andExpect(jsonPath("$[0].start", notNullValue()))
        .andExpect(jsonPath("$[0].end", notNullValue()))
        .andExpect(jsonPath("$[0].item.id", is(bookingDto.getItem().getId()), Long.class))
        .andExpect(jsonPath("$[0].booker.id", is(bookingDto.getBooker().getId()), Long.class))
        .andExpect(jsonPath("$[0].status", is(bookingDto.getStatus().toString())));
  }

  @Test
  void findBookingItemByOwnerId_shouldIsOk() throws Exception {
    when(service.getBookingByOwnerId(anyLong())).thenReturn(List.of(bookingDto));

    mvc.perform(get("/bookings/owner").header("X-Sharer-User-Id", user.getId())
            .content(mapper.writeValueAsString(bookingDto)).characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(bookingDto.getId()), Long.class))
        .andExpect(jsonPath("$[0].start", notNullValue()))
        .andExpect(jsonPath("$[0].end", notNullValue()))
        .andExpect(jsonPath("$[0].item.id", is(bookingDto.getItem().getId()), Long.class))
        .andExpect(jsonPath("$[0].booker.id", is(bookingDto.getBooker().getId()), Long.class))
        .andExpect(jsonPath("$[0].status", is(bookingDto.getStatus().toString())));
  }
}
