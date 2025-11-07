package ru.practicum.event.service.admin;

import ru.practicum.event.dto.EventFullDTO;
import ru.practicum.event.dto.SearchEventsDTO;
import ru.practicum.event.dto.UpdateEventAdminRequest;

import java.util.List;

public interface EventAdminService {
    List<EventFullDTO> getEvents(SearchEventsDTO searchEventsDTO);

    EventFullDTO updateEvent(Integer eventId, UpdateEventAdminRequest request);
}
