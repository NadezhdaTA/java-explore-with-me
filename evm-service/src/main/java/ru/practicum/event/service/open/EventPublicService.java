package ru.practicum.event.service.open;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.event.dto.EventFullDTO;
import ru.practicum.event.dto.EventPublicParams;
import ru.practicum.event.dto.EventShortDTO;

import java.util.List;

public interface EventPublicService {
    EventFullDTO findEventById(Integer id, HttpServletRequest request);

    List<EventShortDTO> findAllEvents(EventPublicParams params, HttpServletRequest request);
}
