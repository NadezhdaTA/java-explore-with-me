package ru.practicum.request.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.requestDTO.ParticipationRequestDto;
import ru.practicum.request.service.RequestPrivateService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestPrivateController {
    private final RequestPrivateService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addRequest(@PathVariable @Positive Integer userId,
                                              @RequestParam @Positive Integer eventId) {
        log.info("Adding request by user with id = {} to event with id = {}", userId, eventId);
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable @Positive Integer userId,
                                                 @PathVariable @Positive Integer requestId) {
        log.info("Canceling request with id = {} for user with id = {}", requestId, userId);
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping
    List<ParticipationRequestDto> getRequests(@PathVariable @Positive Integer userId) {
        log.info("Get requests by user with id = {}", userId);
        return requestService.getRequests(userId);
    }
}
