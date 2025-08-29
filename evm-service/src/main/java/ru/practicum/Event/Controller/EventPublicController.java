package ru.practicum.Event.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Event.DTO.EventFullDTO;
import ru.practicum.Event.DTO.EventPublicParams;
import ru.practicum.Event.DTO.EventShortDTO;
import ru.practicum.Event.Service.Public.EventPublicServiceImpl;

import java.util.List;


@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventPublicController {
    private final EventPublicServiceImpl eventPublicService;

    @GetMapping("/{id}")
    public EventFullDTO findEventById(@PathVariable Integer id) {
        return eventPublicService.findEventById(id);
    }

    @GetMapping
    public List<EventShortDTO> findAllEvents(@ModelAttribute EventPublicParams params) {
        return eventPublicService.findAllEvents(params);
    }
}
