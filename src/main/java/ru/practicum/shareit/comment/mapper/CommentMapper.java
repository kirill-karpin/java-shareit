package ru.practicum.shareit.comment.mapper;

import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.CreateCommentDto;

public class CommentMapper {

  public static CommentDto toCommentDto(Comment comment) {
    return CommentDto.builder()
        .id(comment.getId())
        .text(comment.getText())
        .authorName(comment.getAuthor().getName())
        .build();

  }

  public static Comment toComment(CreateCommentDto dto) {
    return Comment.builder()
        .text(dto.getText())
        .build();
  }
}
