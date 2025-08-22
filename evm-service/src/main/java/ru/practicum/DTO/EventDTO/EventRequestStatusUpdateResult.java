package ru.practicum.DTO.EventDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.DTO.ParticipationDTO.ParticipationRequestDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResult {
    private ParticipationRequestDto confirmedRequests;
    private ParticipationRequestDto rejectedRequests;
}
