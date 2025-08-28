package ru.practicum.Event.Service.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Category.Repository.CategoryRepository;
import ru.practicum.Event.DTO.EventFullDTO;
import ru.practicum.Event.DTO.EventsRequestDTO;
import ru.practicum.Event.DTO.UpdateEventAdminRequest;
import ru.practicum.Event.Mapper.EventMapper;
import ru.practicum.Event.Model.Event;
import ru.practicum.Event.Model.State;
import ru.practicum.Event.Repository.EventRepository;
import ru.practicum.Exception.ConflictException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<EventFullDTO> getEvents(EventsRequestDTO eventsRequestDTO) {
        List<Event> events = new ArrayList<>();
        List<Integer> users = eventsRequestDTO.getUsers();

        for (Integer userId : users) {
            List<Event> eventsForUser = eventRepository.findEventsByInitiatorId(userId);
            events.addAll(eventsForUser);
        }

        List<State> states = eventsRequestDTO.getStates();
        List<Integer> categoryIds = eventsRequestDTO.getCategories();

        return events.stream()
                .filter(event -> states.contains(event.getState()))
                .filter(event -> categoryIds.contains(event.getCategory().getId()))
                .filter(event -> event.getEventDate().isAfter(eventsRequestDTO.getRangeStart()))
                .filter(event -> event.getEventDate().isBefore(eventsRequestDTO.getRangeEnd()))
                .map(eventMapper::toEventFullDTO)
                .toList();

    }

    @Override
    public EventFullDTO updateEvent(Integer eventId, UpdateEventAdminRequest request) {
        Event event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (request.getEventDate() != null) {
            if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
                throw new ConflictException("Event date should be 1 horse after creation date");
            }
        }

        if (request.getStateAction() != null) {
            switch (request.getStateAction()) {
                case PUBLISH_EVENT -> {
                    if (event.getState().equals(State.PENDING)) {
                        event.setState(State.PUBLISHED);
                        event.setPublishedOn(LocalDateTime.now());
                        eventRepository.save(event);
                    } else {
                        throw new ConflictException("Event state should be PENDING");
                    }
                }

                case REJECT_EVENT -> {
                    if (!event.getState().equals(State.PUBLISHED)) {
                        event.setState(State.REJECTED);
                        eventRepository.save(event);
                    } else {
                        throw new ConflictException("Event state should be not PUBLISHED");
                    }
                }
            }
        }

        Event updatedEvent = eventMapper.toEvent(request, event);

        return eventMapper.toEventFullDTO(eventRepository.save(updatedEvent));
    }
}
