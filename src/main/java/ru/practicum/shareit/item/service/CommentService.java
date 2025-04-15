package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CreateCommentDto;

public interface CommentService {

  CommentDto commentBookingPast(Long userId, Long itemId, CreateCommentDto commentDto);


}
