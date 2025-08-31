package ru.practicum.Event.Service.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.Category.Repository.CategoryRepository;
import ru.practicum.Event.DTO.EventFullDTO;
import ru.practicum.Event.DTO.SearchEventsDTO;
import ru.practicum.Event.DTO.UpdateEventAdminRequest;
import ru.practicum.Event.Mapper.EventMapper;
import ru.practicum.Event.Model.Event;
import ru.practicum.Event.Model.State;
import ru.practicum.Event.Repository.EventRepository;
import ru.practicum.Exception.ConflictException;
import ru.practicum.Exception.ValidationException;
import ru.practicum.User.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public List<EventFullDTO> getEvents(SearchEventsDTO dto) {
        if (dto.getRangeStart() != null || dto.getRangeEnd() != null) {
            if (dto.getRangeStart().isAfter(dto.getRangeEnd())) {
                throw new ValidationException("Range start is after range end");
            }
        }

        Specification<Event> spec = getEventSpecification(dto);
        Pageable pageable = PageRequest.of(dto.getFrom(), dto.getSize());

        List<Event> events = eventRepository.findAll(spec, pageable).getContent();

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

        return eventMapper.toEventFullDTO(eventRepository.save(updatedEvent));
    }

    private Specification<Event> getEventSpecification(SearchEventsDTO event) {
        Specification<Event> spec = Specification.where(null);

        if (Objects.nonNull(event.getUsers()) && !event.getUsers().isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("initiator").get("id").in(
                    event.getUsers().stream().filter(Objects::nonNull).toList()));
        }

        if (Objects.nonNull(event.getCategories() ) && !event.getCategories().isEmpty()) {
            spec = spec.and((root, query, builder) ->
                    root.get("category").get("id").in(event.getCategories().stream()
                            .filter(Objects::nonNull)
                            .toList()));
        }

        if (Objects.nonNull(event.getStates() ) && !event.getStates().isEmpty()) {
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
