package ru.practicum.comment.service.close;

import ru.practicum.comment.dto.CommentDTO;
import ru.practicum.comment.dto.NewCommentDTO;
import ru.practicum.comment.dto.UpdateCommentDTO;

public interface CommentPrivateService {
    CommentDTO addComment(NewCommentDTO commentDTO, Integer userId, Integer eventId);

    CommentDTO getComment(Integer commentId);

    CommentDTO updateComment(UpdateCommentDTO commentDTO, Integer userId, Integer commentId);

    void deleteComment(Integer commentId, Integer userId);
}
