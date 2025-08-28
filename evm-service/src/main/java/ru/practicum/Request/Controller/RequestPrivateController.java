package ru.practicum.Request.Controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Request.RequestDTO.ParticipationRequestDto;
import ru.practicum.Request.Service.RequestPrivateServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class RequestPrivateController {
    private final RequestPrivateServiceImpl requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addRequest(@PathVariable @Positive Integer userId,
                                              @RequestParam @PositiveOrZero Integer eventId) {
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable @Positive Integer userId,
                                                 @PathVariable @Positive Integer requestId) {
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping
    List<ParticipationRequestDto> getRequests(@PathVariable @Positive Integer userId) {
        return requestService.getRequests(userId);
    }
}
