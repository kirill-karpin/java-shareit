package ru.practicum.shareit.booking.service;

import jakarta.validation.Valid;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;

public interface BookingService {

  BookingDto create(Long userId, @Valid CreateBookingDto createBookingDto);
}
