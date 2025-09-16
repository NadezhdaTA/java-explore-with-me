package ru.practicum.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.comment.model.Status;
import ru.practicum.event.dto.EventCommentsDTO;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Integer id;

    private String comment;

    private User author;

    private EventCommentsDTO event;

    private LocalDateTime createdAt;

    private Status status;
}
