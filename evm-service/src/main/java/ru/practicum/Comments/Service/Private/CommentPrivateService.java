package ru.practicum.Comments.Service.Private;

import ru.practicum.Comments.DTO.CommentDTO;
import ru.practicum.Comments.DTO.NewCommentDTO;
import ru.practicum.Comments.DTO.UpdateCommentDTO;

public interface CommentPrivateService {
    CommentDTO addComment(NewCommentDTO commentDTO, Integer userId, Integer eventId);

    CommentDTO getComment(Integer commentId);

    CommentDTO updateComment(UpdateCommentDTO commentDTO, Integer userId, Integer commentId);

    void deleteComment(Integer commentId, Integer userId);
}
