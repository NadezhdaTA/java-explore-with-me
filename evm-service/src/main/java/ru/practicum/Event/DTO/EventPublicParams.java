package ru.practicum.Event.DTO;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.StatsRequestDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventPublicParams {
    private String text;
    public ArrayList<Integer> categories;
    private Boolean paid;

    @DateTimeFormat(pattern = StatsRequestDTO.DATE_FORMAT)
    private LocalDateTime rangeStart;

    @DateTimeFormat(pattern = StatsRequestDTO.DATE_FORMAT)
    private LocalDateTime rangeEnd;

    private Boolean onlyAvailable = false;

    private String sort;

    @PositiveOrZero
    private Integer from = 0;

    @Positive
    private Integer size = 10;
}
