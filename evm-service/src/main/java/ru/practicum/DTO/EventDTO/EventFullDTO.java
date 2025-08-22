package ru.practicum.DTO.EventDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.DTO.CategoryDTO.CategoryDTO;
import ru.practicum.User.DTO.UserShortDTO;
import ru.practicum.Model.Location;
import ru.practicum.Model.State;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDTO {
    private Integer id;

    @NotBlank
    private String annotation;

    @NotNull
    private CategoryDTO category;

    private Integer confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    private String description;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private UserShortDTO initiator;

    @NotNull
    private Location location;

    @NotNull
    private Boolean paid;

    @Positive
    private Integer participantLimit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private State state;

    @NotBlank
    private String title;

    private Integer views;
}
