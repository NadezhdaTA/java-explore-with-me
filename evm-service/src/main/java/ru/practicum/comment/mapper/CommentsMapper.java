package ru.practicum.comment.mapper;

import org.mapstruct.Mapper;
import ru.practicum.comment.dto.CommentDTO;
import ru.practicum.comment.dto.NewCommentDTO;
import ru.practicum.comment.dto.UpdateCommentDTO;
import ru.practicum.comment.dto.UpdatedCommentDTO;
import ru.practicum.comment.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    CommentDTO toCommentDTO(Comment comment);

    Comment toComment(NewCommentDTO commentDTO);

    UpdatedCommentDTO toUpdatedCommentDTO(Comment comment);

    Comment toComment(UpdateCommentDTO commentDTO);
}
