package ru.practicum.Comments.Mapper;

import org.mapstruct.Mapper;
import ru.practicum.Comments.DTO.CommentDTO;
import ru.practicum.Comments.DTO.NewCommentDTO;
import ru.practicum.Comments.DTO.UpdateCommentDTO;
import ru.practicum.Comments.DTO.UpdatedCommentDTO;
import ru.practicum.Comments.Model.Comment;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    CommentDTO toCommentDTO(Comment comment);

    Comment toComment(NewCommentDTO commentDTO);

    UpdatedCommentDTO toUpdatedCommentDTO(Comment comment);

    Comment toComment(UpdateCommentDTO commentDTO);
}
