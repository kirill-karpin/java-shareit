package ru.practicum.shareit.comment.service;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.CreateCommentDto;

public interface CommentService {

  CommentDto commentBookingPast(Long userId, Long itemId, CreateCommentDto commentDto);


}
