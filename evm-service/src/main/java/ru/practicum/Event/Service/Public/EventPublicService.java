package ru.practicum.Event.Service.Public;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.Event.DTO.EventFullDTO;
import ru.practicum.Event.DTO.EventPublicParams;
import ru.practicum.Event.DTO.EventShortDTO;

import java.util.List;

public interface EventPublicService {
    EventFullDTO findEventById(Integer id, HttpServletRequest request);

    List<EventShortDTO> findAllEvents(EventPublicParams params, HttpServletRequest request);
}
