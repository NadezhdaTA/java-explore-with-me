package ru.practicum.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;
import ru.practicum.event.service.close.EventPrivateService;
import ru.practicum.request.requestDTO.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class EventPrivateController {
    private final EventPrivateService eventPrivateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDTO addEvent(@PathVariable Integer userId, @RequestBody @Valid NewEventDTO event) {
        log.info("Adding new event {}", event);
        return eventPrivateService.addEvent(userId, event);
    }

    @GetMapping
    public List<EventShortDTO> getEvents(@PathVariable Integer userId,
                                         @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                         @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Getting events by user with id = {} from {} to {}", userId, from, size);
        return eventPrivateService.getEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDTO getEvent(@PathVariable Integer userId, @PathVariable Integer eventId) {
        log.info("Getting event with id {} by user with id = {}", eventId, userId);
        return eventPrivateService.getEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDTO updateEvent(@PathVariable Integer userId,
                                    @PathVariable Integer eventId,
                                    @RequestBody @Valid UpdateEventUserRequest event) {
        log.info("Updating event with id {} by user with id = {}: {}", eventId, userId, event);
        return eventPrivateService.updateEvent(userId, eventId, event);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Integer userId,
                                                         @PathVariable Integer eventId) {
        log.info("Getting user participation requests by user with id = {} and event with id = {}", userId, eventId);
        return eventPrivateService.getUserRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequests(@RequestBody @Valid EventRequestStatusUpdateRequest request,
                                                         @PathVariable Integer userId,
                                                         @PathVariable Integer eventId) {
        log.info("Updating participation request in event with id {} by user with id = {}: {}", eventId, userId, request);
        return eventPrivateService.updateRequests(request, userId, eventId);
    }

}
