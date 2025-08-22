package ru.practicum.DTO.CompilationDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.DTO.EventDTO.EventShortDTO;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDTO {
    @NotNull
    private Integer id;

    @NotNull
    private Boolean pinned;

    @NotBlank
    private String title;

    private Set<EventShortDTO> events;
}
