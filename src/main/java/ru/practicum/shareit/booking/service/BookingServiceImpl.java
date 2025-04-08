package ru.practicum.shareit.booking.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
public class BookingServiceImpl implements BookingService {

  private final BookingRepository bookingRepository;
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;

  public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository,
      ItemRepository itemRepository) {
    this.bookingRepository = bookingRepository;
    this.userRepository = userRepository;
    this.itemRepository = itemRepository;
  }

  @Override
  public BookingDto create(Long userId, CreateBookingDto createBookingDto) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));

    Item item = itemRepository.findById(createBookingDto.getItemId())
        .orElseThrow(() -> new NotFoundException("Item not found"));

    if (!item.getIsAvailable()) {
      throw new RuntimeException("Item not available");
    }

    Booking createdBooking = BookingMapper.toBooking(createBookingDto);

    createdBooking.setItem(item);
    createdBooking.setBooker(user);
    createdBooking.setStatus(BookingStatus.WAITING);

    Booking saveBooking = bookingRepository.save(createdBooking);

    return BookingMapper.toBookingDto(saveBooking);
  }
}
