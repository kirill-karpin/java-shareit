package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.Comment;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CreateCommentDto;

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
