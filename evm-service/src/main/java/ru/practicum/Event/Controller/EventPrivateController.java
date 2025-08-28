package ru.practicum.Event.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Event.DTO.EventFullDTO;
import ru.practicum.Event.DTO.NewEventDTO;
import ru.practicum.Event.DTO.UpdateEventUserRequest;
import ru.practicum.Event.Service.Private.EventPrivateServiceImpl;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventPrivateController {
    private final EventPrivateServiceImpl eventPrivateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDTO addEvent(@PathVariable Integer userId, @RequestBody @Valid NewEventDTO event) {
        return eventPrivateService.addEvent(userId, event);
    }

    @GetMapping("/{eventId}")
    public EventFullDTO getEvent(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventPrivateService.getEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDTO updateEvent(@PathVariable Integer userId,
                                    @PathVariable Integer eventId, @RequestBody @Valid UpdateEventUserRequest event) {
        return eventPrivateService.updateEvent(userId, eventId, event);
    }

}
