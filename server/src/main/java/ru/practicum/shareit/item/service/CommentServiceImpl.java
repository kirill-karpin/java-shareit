package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.Comment;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final BookingRepository bookingRepository;

  public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository,
      ItemRepository itemRepository, BookingRepository bookingRepository) {
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.itemRepository = itemRepository;
    this.bookingRepository = bookingRepository;
  }

  @Override
  public CommentDto commentBookingPast(Long userId, Long itemId, CreateCommentDto commentDto) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));

    Item item = itemRepository.findById(itemId)
        .orElseThrow(() -> new NotFoundException("item not found"));

    Booking booking = bookingRepository.checkBookingIsExpired(
        userId, itemId);

    if (booking == null) {
      throw new RuntimeException("booking not found");
    }

    Comment comment = CommentMapper.toComment(commentDto);

    comment.setAuthor(user);
    comment.setItem(item);

    return CommentMapper.toCommentDto(commentRepository.save(comment));
  }
}
