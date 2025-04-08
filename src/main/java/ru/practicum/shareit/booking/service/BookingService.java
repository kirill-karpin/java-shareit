package ru.practicum.shareit.booking.service;

import jakarta.validation.Valid;
import java.util.List;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;

public interface BookingService {

  BookingDto create(Long userId, @Valid CreateBookingDto createBookingDto);

  BookingDto approve(Long userId, Long bookingId, Boolean approved);

  BookingDto getById(Long bookingId);

  BookingDto getByOwnerIdOrBookerId(Long bookingId, Long userId);

  List<BookingDto> getBookingByBookerId(Long userId);

  List<BookingDto> getBookingByOwnerId(Long userId);
}

