package ru.practicum.Comments.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Comments.Model.Status;
import ru.practicum.Event.DTO.EventCommentsDTO;
import ru.practicum.User.Model.User;

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
