package ru.practicum.event.service.close;

import ru.practicum.request.requestDTO.ParticipationRequestDto;
import ru.practicum.event.dto.*;

import java.util.List;

public interface EventPrivateService {
    EventFullDTO addEvent(Integer userId, NewEventDTO event);

    EventFullDTO getEvent(Integer userId, Integer eventId);

    EventFullDTO updateEvent(Integer userId, Integer eventId, UpdateEventUserRequest event);

    List<ParticipationRequestDto> getUserRequests(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult updateRequests(EventRequestStatusUpdateRequest request, Integer userId, Integer eventId);

    List<EventShortDTO> getEvents(Integer userId, Integer from, Integer size);
}
