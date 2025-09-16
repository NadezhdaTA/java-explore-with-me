package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDTO;
import ru.practicum.StatsRequestDTO;
import ru.practicum.user.dto.UserShortDTO;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.State;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDTO {
    private Integer id;

    private String annotation;

    private CategoryDTO category;

    private Integer confirmedRequests;

    @JsonFormat(pattern = StatsRequestDTO.DATE_FORMAT)
    private LocalDateTime createdOn;

    private String description;

    @JsonFormat(pattern = StatsRequestDTO.DATE_FORMAT)
    private LocalDateTime eventDate;

    private UserShortDTO initiator;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    @JsonFormat(pattern = StatsRequestDTO.DATE_FORMAT)
    private LocalDateTime publishedOn = LocalDateTime.now();

    private Boolean requestModeration;

    private State state;

    private String title;

    private Integer views;
}
