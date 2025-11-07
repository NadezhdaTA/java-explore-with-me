package ru.practicum.request.service;

import ru.practicum.request.requestDTO.ParticipationRequestDto;

import java.util.List;

public interface RequestPrivateService {
    ParticipationRequestDto addRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelRequest(Integer userId, Integer requestId);

    List<ParticipationRequestDto> getRequests(Integer userId);
}
