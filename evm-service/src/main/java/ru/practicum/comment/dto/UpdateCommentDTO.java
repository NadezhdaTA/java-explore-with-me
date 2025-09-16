package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.StatsRequestDTO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentDTO {

    @NotNull
    @Size(max = 7000)
    @JsonProperty("comment")
    private String comment;

    @DateTimeFormat(pattern = StatsRequestDTO.DATE_FORMAT)
    private final LocalDateTime updatedAt = LocalDateTime.now();
}
