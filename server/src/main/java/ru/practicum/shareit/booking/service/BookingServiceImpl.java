package ru.practicum.shareit.booking.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
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
      throw new ValidationException("Item not available");
    }

    Booking createdBooking = BookingMapper.toBooking(createBookingDto);

    createdBooking.setItem(item);
    createdBooking.setBooker(user);
    createdBooking.setStatus(BookingStatus.WAITING);

    Booking saveBooking = bookingRepository.save(createdBooking);

    return BookingMapper.toBookingDto(saveBooking);
  }

  @Override
  public BookingDto approve(Long userId, Long bookingId, Boolean approved) {
    userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new NotFoundException("Booking not found"));
    if (approved) {
      booking.setStatus(BookingStatus.APPROVED);
    } else {
      booking.setStatus(BookingStatus.REJECTED);
    }

    return BookingMapper.toBookingDto(bookingRepository.save(booking));
  }

  @Override
  public BookingDto getById(Long bookingId) {
    return BookingMapper.toBookingDto(bookingRepository.findById(bookingId)
        .orElseThrow(() -> new NotFoundException("Booking not found")));
  }

  @Override
  public BookingDto getByOwnerIdOrBookerId(Long id, Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));

    return BookingMapper.toBookingDto(bookingRepository.findOneByBookerOrItemOwner(id, user));
  }

  @Override
  public List<BookingDto> getBookingByBookerId(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));

    return bookingRepository.findAllByBooker(user).stream()
        .map(BookingMapper::toBookingDto).toList();
  }

  @Override
  public List<BookingDto> getBookingByOwnerId(Long userId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));

    return bookingRepository.findAllByItem_Owner(user);
  }
}
