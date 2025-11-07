package ru.practicum.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDTO;
import ru.practicum.event.dto.EventPublicParams;
import ru.practicum.event.dto.EventShortDTO;
import ru.practicum.event.service.open.EventPublicService;

import java.util.List;


@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventPublicController {
    private final EventPublicService eventPublicService;

    @GetMapping("/{id}")
    public EventFullDTO findEventById(@PathVariable Integer id, HttpServletRequest request) {
        log.info("Finding event with id {}", id);
        return eventPublicService.findEventById(id, request);
    }

    @GetMapping
    public List<EventShortDTO> findAllEvents(@ModelAttribute @Valid EventPublicParams params,
                                             HttpServletRequest request) {
        log.info("Finding all events with {} params", params);
        return eventPublicService.findAllEvents(params, request);
    }
}
