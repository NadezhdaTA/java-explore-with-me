package ru.practicum.DTO.EventDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.Category.DTO.CategoryDTO;
import ru.practicum.Model.Location;
import ru.practicum.Model.State;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventAdminRequest {

    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;

    @NotNull
    private CategoryDTO category;

    @NotBlank
    @Length(min = 20, max = 7000)
    private String description;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private State stateAction;

    @NotBlank
    @Length(min = 3, max = 120)
    private String title;

}
