package ru.practicum.shareit.booking.mapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.mapper.UserMapper;

public class BookingMapper {

  public static BookingDto toBookingDto(Booking booking) {
    return BookingDto.builder()
        .id(booking.getId())
        .start(LocalDateTime.ofInstant(booking.getStartDate(), ZoneOffset.ofHours(0)))
        .end(LocalDateTime.ofInstant(booking.getEndDate(), ZoneOffset.ofHours(0)))
        .status(booking.getStatus())
        .booker(UserMapper.toUserDto(booking.getBooker()))
        .item(ItemMapper.toItemDto(booking.getItem()))
        .build();
  }

  public static Booking toBooking(BookingDto bookingDto) {
    return Booking.builder().
        id(bookingDto.getId()).build();
  }

  public static Booking toBooking(CreateBookingDto bookingDto) {
    return Booking.builder()
        .startDate(bookingDto.getStart().toInstant(ZoneOffset.ofHours(0)))
        .endDate(bookingDto.getEnd().toInstant(ZoneOffset.ofHours(0)))
        .build();
  }
}
