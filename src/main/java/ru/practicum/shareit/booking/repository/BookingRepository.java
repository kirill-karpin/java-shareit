package ru.practicum.shareit.booking.repository;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  @Query("SELECT booking FROM Booking booking "
      + "JOIN FETCH booking.item "
      + "JOIN FETCH booking.booker "
      + "WHERE booking.id = :bookingId and (booking.booker = :user or booking.item.owner = :user)")
  Booking findOneByBookerOrItemOwner(@NotNull Long bookingId, @NotNull User user);

  List<Booking> findAllByBooker(@NotNull User booker);

  List<BookingDto> findAllByItem_Owner(@NotNull User itemOwner);

  @Query("SELECT booking FROM Booking booking "
      + "JOIN FETCH booking.item "
      + "JOIN FETCH booking.booker "
      + "WHERE booking.booker.id = :bookerId "
      + "and booking.item.id = :itemId and booking.endDate <= CURRENT_TIMESTAMP "
      + "and booking.status = 'APPROVED'")
  Booking checkBookingIsExpired(@NotNull Long bookerId, @NotNull Long itemId);
}
