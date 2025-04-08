package ru.practicum.shareit.booking.service;

import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.repository.UserRepository;

@DataJpaTest
class BookingServiceImplTest {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ItemRepository itemRepository;


  @Test
  void createBooking() {
    User user1 = User.builder()
        .name("test")
        .email("test@mail.ru")
        .build();

    userRepository.save(user1);

    Item item = Item.builder()
        .name("item")
        .isAvailable(true)
        .description("test")
        .owner(user1)
        .build();

    itemRepository.save(item);

    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setEmail("test2@mail.ru");
    updateUserDto.setName("name2");

  }
}
