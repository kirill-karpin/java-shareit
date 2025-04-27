package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.service.BookingService;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

  private static final String USER_ID_HEADER = "X-Sharer-User-Id";

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @PostMapping
  public BookingDto addBooking(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestBody @Valid CreateBookingDto createBookingDto) {

    return bookingService.create(userId, createBookingDto);
  }


  @GetMapping("/{bookingId}")
  public BookingDto getBooking(@RequestHeader(USER_ID_HEADER) Long userId,
      @PathVariable Long bookingId) {

    return bookingService.getByOwnerIdOrBookerId(bookingId, userId);
  }

  @PatchMapping("/{bookingId}")
  public BookingDto approveBooking(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestParam Boolean approved, @PathVariable Long bookingId) {
    return bookingService.approve(userId, bookingId, approved);
  }

  @GetMapping
  public List<BookingDto> getBookingByBookerId(@RequestHeader(USER_ID_HEADER) Long userId) {
    return bookingService.getBookingByBookerId(userId);
  }

  @GetMapping("/owner")
  public List<BookingDto> getBookingByOwnerId(@RequestHeader(USER_ID_HEADER) Long userId) {
    return bookingService.getBookingByOwnerId(userId);
  }


}
