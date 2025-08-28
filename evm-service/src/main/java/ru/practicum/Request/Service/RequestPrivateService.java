package ru.practicum.Request.Service;

import ru.practicum.Request.RequestDTO.ParticipationRequestDto;

import java.util.List;

public interface RequestPrivateService {
    ParticipationRequestDto addRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelRequest(Integer userId, Integer requestId);

    List<ParticipationRequestDto> getRequests(Integer userId);
}
