package ru.practicum.comment.service.open;

import ru.practicum.comment.dto.CommentDTO;

import java.util.List;

public interface CommentPublicService {
    List<CommentDTO> getComments(Integer eventId, Integer from, Integer size);
}
