package ru.practicum.Event.Service.Public;

import ru.practicum.Event.DTO.EventFullDTO;
import ru.practicum.Event.DTO.EventPublicParams;
import ru.practicum.Event.DTO.EventShortDTO;

import java.util.List;

public interface EventPublicService {
    EventFullDTO findEventById(Integer id);

    List<EventShortDTO> findAllEvents(EventPublicParams params);
}
