package ru.practicum.shareit.booking.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

  @Mock
  private BookingRepository bookingRepository;
  @Mock
  private ItemRepository itemRepository;
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private BookingServiceImpl bookingService;

  private UserService userService;

  private ItemService itemService;

  private User user;
  private User owner;
  private Item item;
  private CreateBookingDto createBookingDto;
  private Booking booking;

  @BeforeEach
  void setUp() {
    user = User.builder().id(1L).name("UserName").email("test@example.com").build();
    owner = User.builder().id(2L).name("OwnerName").email("test2@example.com").build();
    item = Item.builder().id(2L).name("name").description("description").owner(owner)
        .isAvailable(true).build();
    createBookingDto = CreateBookingDto.builder()
        .itemId(item.getId())
        .start(LocalDateTime.now())
        .end(LocalDateTime.now().plusMinutes(10))
        .build();
    booking = BookingMapper.toBooking(createBookingDto);
    booking.setItem(item);
    booking.setBooker(user);
    booking.setStatus(BookingStatus.WAITING);
  }

  @Test
  void addBookingValidationExceptionItemOnUnAvaliable() {
    item.setIsAvailable(false);
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));

    ValidationException exception = assertThrows(ValidationException.class, () -> {
      bookingService.create(user.getId(), createBookingDto);
    });
    assertEquals("Item not available", exception.getMessage());
    verify(userRepository).findById(user.getId());
    verify(itemRepository).findById(item.getId());
  }

}
