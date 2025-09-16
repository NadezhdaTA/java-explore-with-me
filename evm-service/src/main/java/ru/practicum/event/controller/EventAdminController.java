package ru.practicum.event.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDTO;
import ru.practicum.event.dto.SearchEventsDTO;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.service.admin.EventAdminService;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
public class EventAdminController {
    private final EventAdminService eventAdminService;

    @GetMapping
    public List<EventFullDTO> getEvents(@ModelAttribute @Valid SearchEventsDTO dto) {
        log.info("Searching events with parameters {}", dto);
        return eventAdminService.getEvents(dto);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDTO updateEvent(@PathVariable Integer eventId,
                                    @RequestBody @Valid UpdateEventAdminRequest request) {
        log.info("Updating event with id {}: {}", eventId, request);
        return eventAdminService.updateEvent(eventId, request);
    }

}
