package ru.practicum.Event.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Event.Model.Location;
import ru.practicum.Event.Model.State;
import ru.practicum.StatsRequestDTO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventUserRequest {

    @Size(min = 20, max = 2000)
    private String annotation;

    private Integer categoryId;

    @Size(min = 20, max = 7000)
    private String description;

    @JsonFormat(pattern = StatsRequestDTO.DATE_FORMAT)
    @Future
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;

    private State stateAction;

    @Size(min = 3, max = 120)
    private String title;
}
