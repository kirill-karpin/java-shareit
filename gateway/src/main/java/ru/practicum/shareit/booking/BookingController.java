package ru.practicum.shareit.booking;

import static ru.practicum.shareit.Config.USER_ID_HEADER;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {

  private final BookingClient bookingClient;

  @GetMapping
  public ResponseEntity<Object> getBookings(@RequestHeader(USER_ID_HEADER) long userId,
      @RequestParam(name = "state", defaultValue = "all") String stateParam,
      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
    BookingState state = BookingState.from(stateParam)
        .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
    log.info("Get booking with state {}, userId={}, from={}, size={}", stateParam, userId, from,
        size);
    return bookingClient.getBookings(userId, state, from, size);
  }

  @PostMapping
  public ResponseEntity<Object> bookItem(@RequestHeader(USER_ID_HEADER) long userId,
      @RequestBody @Valid BookItemRequestDto requestDto) {
    log.info("Creating booking {}, userId={}", requestDto, userId);
    return bookingClient.bookItem(userId, requestDto);
  }

  @GetMapping("/{bookingId}")
  public ResponseEntity<?> getBooking(@RequestHeader(USER_ID_HEADER) long userId,
      @PathVariable Long bookingId) {
    log.info("Get booking {}, userId={}", bookingId, userId);
    return bookingClient.getBooking(userId, bookingId);
  }


  @PatchMapping("/{bookingId}")
  public ResponseEntity<?> approveBooking(@RequestHeader(USER_ID_HEADER) Long userId,
      @RequestParam Boolean approved, @PathVariable Long bookingId) {
    return bookingClient.approve(userId, bookingId, approved);
  }

  @GetMapping("/owner")
  public ResponseEntity<?> getBookingByOwnerId(@RequestHeader(USER_ID_HEADER) Long userId) {
    return bookingClient.getBookingByOwnerId(userId);
  }
}
