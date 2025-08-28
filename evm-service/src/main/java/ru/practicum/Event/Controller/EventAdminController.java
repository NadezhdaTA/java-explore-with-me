package ru.practicum.Event.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Event.DTO.EventFullDTO;
import ru.practicum.Event.DTO.EventsRequestDTO;
import ru.practicum.Event.DTO.UpdateEventAdminRequest;
import ru.practicum.Event.Service.Admin.EventAdminService;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventAdminController {
    private final EventAdminService eventAdminService;

    @GetMapping
    public List<EventFullDTO> getEvents(@ModelAttribute EventsRequestDTO eventsRequestDTO) {
        return null;
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDTO updateEvent(@PathVariable Integer eventId,
                                    @RequestBody UpdateEventAdminRequest request) {
        return eventAdminService.updateEvent(eventId, request);
    }

}
