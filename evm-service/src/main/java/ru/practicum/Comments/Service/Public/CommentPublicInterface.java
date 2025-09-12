package ru.practicum.Comments.Service.Public;

import ru.practicum.Comments.DTO.CommentDTO;

import java.util.List;

public interface CommentPublicInterface {
    List<CommentDTO> getComments(Integer eventId, Integer from, Integer size);
}
