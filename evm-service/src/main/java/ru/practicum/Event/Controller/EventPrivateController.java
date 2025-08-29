package ru.practicum.Event.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Event.DTO.*;
import ru.practicum.Event.Service.Private.EventPrivateServiceImpl;
import ru.practicum.Request.RequestDTO.ParticipationRequestDto;

import java.util.List;

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

    @GetMapping
    public List<EventShortDTO> getEvents(@PathVariable Integer userId,
                                         @RequestParam Integer from,
                                         @RequestParam Integer size) {
        return eventPrivateService.getEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDTO getEvent(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventPrivateService.getEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDTO updateEvent(@PathVariable Integer userId,
                                    @PathVariable Integer eventId,
                                    @RequestBody @Valid UpdateEventUserRequest event) {
        return eventPrivateService.updateEvent(userId, eventId, event);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Integer userId,
                                                         @PathVariable Integer eventId) {
        return eventPrivateService.getUserRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequests(@RequestBody @Valid EventRequestStatusUpdateRequest request,
                                                         @PathVariable Integer userId,
                                                         @PathVariable Integer eventId) {
        return eventPrivateService.updateRequests(request, userId, eventId);
    }

}
