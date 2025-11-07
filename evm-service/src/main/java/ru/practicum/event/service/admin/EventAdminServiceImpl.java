package ru.practicum.event.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.dto.EventFullDTO;
import ru.practicum.event.dto.SearchEventsDTO;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.ValidationException;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventAdminServiceImpl implements EventAdminService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public List<EventFullDTO> getEvents(SearchEventsDTO dto) {
        if (dto.getRangeStart() != null && dto.getRangeEnd() != null) {
            if (dto.getRangeStart().isAfter(dto.getRangeEnd())) {
                throw new ValidationException("Range start is after range end");
            }
        }

        Specification<Event> spec = getEventSpecification(dto);
        Pageable pageable = PageRequest.of(dto.getFrom(), dto.getSize());

        List<Event> events = eventRepository.findAll(spec, pageable).getContent();
        log.info("Found events with params {} are sent, size - {}", dto, events.size());

        return events.stream()
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
        EventFullDTO eventFullDTO = eventMapper.toEventFullDTO(eventRepository.save(updatedEvent));
        log.info("Event with id = {} and parameters {} is updated: {}", eventId, request, eventFullDTO);

        return eventFullDTO;
    }

    private Specification<Event> getEventSpecification(SearchEventsDTO event) {
        Specification<Event> spec = Specification.where(null);

        if (Objects.nonNull(event.getUsers()) && !event.getUsers().isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("initiator").get("id").in(
                    event.getUsers().stream().filter(Objects::nonNull).toList()));
        }

        if (Objects.nonNull(event.getCategories()) && !event.getCategories().isEmpty()) {
            spec = spec.and((root, query, builder) ->
                    root.get("category").get("id").in(event.getCategories().stream()
                            .filter(Objects::nonNull)
                            .toList()));
        }

        if (Objects.nonNull(event.getStates()) && !event.getStates().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    root.get("state").in(event.getStates().stream()
                    .filter(Objects::nonNull)
                    .toList()));
        }

        if (Objects.nonNull(event.getRangeStart())) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("eventDate"), event.getRangeStart()));
        }

        if (Objects.nonNull(event.getRangeEnd())) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("eventDate"), event.getRangeEnd()));
        }

        return spec;
    }
}
