package ru.practicum.DTO.EventDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.DTO.CategoryDTO.CategoryDTO;
import ru.practicum.User.DTO.UserShortDTO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDTO {
    private Integer id;

    @NotBlank
    private String annotation;

    @NotNull
    private CategoryDTO category;

    private Integer confirmedRequests;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private UserShortDTO initiator;

    @NotNull
    private Boolean paid;

    @NotBlank
    private String title;

    private Integer views;
}
