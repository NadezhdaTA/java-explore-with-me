package ru.practicum.Event.Service.Private;

import ru.practicum.Event.DTO.EventFullDTO;
import ru.practicum.Event.DTO.NewEventDTO;
import ru.practicum.Event.DTO.UpdateEventUserRequest;

public interface EventPrivateService {
    EventFullDTO addEvent(Integer userId, NewEventDTO event);

    EventFullDTO getEvent(Integer userId, Integer eventId);

    EventFullDTO updateEvent(Integer userId, Integer eventId, UpdateEventUserRequest event);
}
