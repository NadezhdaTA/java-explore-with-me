package ru.practicum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class StatsRequestDTO {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @DateTimeFormat(pattern = DATE_FORMAT)
    @NotNull(message = "Start date is required")
    private final LocalDateTime start;

    @DateTimeFormat(pattern = DATE_FORMAT)
    @NotNull(message = "End date is required")
    private final LocalDateTime end;

    private List<String> uris;
    private Boolean unique;

}
