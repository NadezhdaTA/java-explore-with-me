package ru.practicum.Event.Service.Admin;

import ru.practicum.Event.DTO.EventFullDTO;
import ru.practicum.Event.DTO.EventsRequestDTO;
import ru.practicum.Event.DTO.UpdateEventAdminRequest;

import java.util.List;

public interface EventAdminService {
    List<EventFullDTO> getEvents(EventsRequestDTO eventsRequestDTO);

    EventFullDTO updateEvent(Integer eventId, UpdateEventAdminRequest request);
}
