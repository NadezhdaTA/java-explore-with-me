package ru.practicum.Event.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCommentsDTO {
    private Integer id;

    private String title;

    private String annotation;

    private LocalDateTime eventDate;

}
