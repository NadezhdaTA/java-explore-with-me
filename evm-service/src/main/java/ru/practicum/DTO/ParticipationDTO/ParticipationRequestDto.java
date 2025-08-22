package ru.practicum.DTO.ParticipationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Model.State;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private Integer id;
    private final LocalDateTime created = LocalDateTime.now();
    private Integer event;
    private Integer requester;
    private State status;
}
