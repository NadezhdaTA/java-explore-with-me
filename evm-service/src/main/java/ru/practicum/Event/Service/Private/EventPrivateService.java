package ru.practicum.Event.Service.Private;

import ru.practicum.Event.DTO.*;
import ru.practicum.Request.RequestDTO.ParticipationRequestDto;

import java.util.List;

public interface EventPrivateService {
    EventFullDTO addEvent(Integer userId, NewEventDTO event);

    EventFullDTO getEvent(Integer userId, Integer eventId);

    EventFullDTO updateEvent(Integer userId, Integer eventId, UpdateEventUserRequest event);

    List<ParticipationRequestDto> getUserRequests(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult updateRequests(EventRequestStatusUpdateRequest request, Integer userId, Integer eventId);

    List<EventShortDTO> getEvents(Integer userId, Integer from, Integer size);
}
