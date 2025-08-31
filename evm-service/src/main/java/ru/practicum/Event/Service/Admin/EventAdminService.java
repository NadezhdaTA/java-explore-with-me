package ru.practicum.Event.Service.Admin;

import ru.practicum.Event.DTO.EventFullDTO;
import ru.practicum.Event.DTO.SearchEventsDTO;
import ru.practicum.Event.DTO.UpdateEventAdminRequest;

import java.util.List;

public interface EventAdminService {
    List<EventFullDTO> getEvents(SearchEventsDTO searchEventsDTO);

    EventFullDTO updateEvent(Integer eventId, UpdateEventAdminRequest request);
}
