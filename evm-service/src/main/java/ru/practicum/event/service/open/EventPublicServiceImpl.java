package ru.practicum.event.service.open;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.dto.EventFullDTO;
import ru.practicum.event.dto.EventPublicParams;
import ru.practicum.event.dto.EventShortDTO;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.StatsClient;
import ru.practicum.ViewStatsDTO;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Integer.parseInt;


@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublicServiceImpl implements EventPublicService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final StatsClient statsClient;

    @Override
    public EventFullDTO findEventById(Integer id, HttpServletRequest request) {
        Event event = eventRepository.findEventsByIdAndState(id, State.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " not found"));

        statsClient.createHit(request, "ewm-main-service");

        List<String> uris = List.of(request.getRequestURI());
        Map<Integer, Integer> views = getStats(uris, request);
        event.setViews(views.get(event.getId()));

        EventFullDTO eventFullDTO = eventMapper.toEventFullDTO(event);
        log.info("Event with id = {} and is found: {}", id, eventFullDTO);
        return eventFullDTO;
    }

    @Override
    public List<EventShortDTO> findAllEvents(EventPublicParams params, HttpServletRequest request) {
        statsClient.createHit(request, "ewm-main-service");

        if (params.getRangeStart() != null || params.getRangeEnd() != null) {
            if (params.getRangeStart().isAfter(params.getRangeEnd())) {
                throw new ValidationException("Range start is after range end");
            }
        }

        Specification<Event> spec = eventSpecification(params);

        Sort sort = null;
        if (params.getSort() != null) {
            switch (params.getSort()) {
                case "EVENT_DATE" -> sort = Sort.by(Sort.Direction.DESC, "eventDate");
                case "VIEWS" -> sort = Sort.by(Sort.Direction.ASC, "views");
            }
        } else {
            sort = Sort.by(Sort.Direction.DESC, "eventDate");
        }


        Pageable pageable = PageRequest.of(params.getFrom(), params.getSize(), sort);

        List<EventShortDTO> events = eventRepository.findAll(spec, pageable).getContent().stream()
                .map(eventMapper::toEventShortDTO)
                .toList();

        List<String> uris = events.stream()
                .map(event -> {
                    return request.getRequestURI() + "/" + event.getId();
                })
                .toList();

        if (!uris.isEmpty()) {
            Map<Integer, Integer> views = getStats(uris, request);
            events.stream()
                    .peek(event -> {
                        if (views.containsKey(event.getId())) {
                            event.setViews(views.get(event.getId()));
                        }
                    })
                    .toList();
        }

        log.info("Found {} events", events.size());
        return events;
    }

    private Map<Integer, Integer> getStats(List<String> uris, HttpServletRequest request) {
        if (uris.isEmpty()) {
            return Collections.emptyMap();
        }

        LocalDateTime start = LocalDateTime.now().minusMonths(6);
        LocalDateTime end = LocalDateTime.now().plusMinutes(1);
        List<ViewStatsDTO> viewStatsDTOS = statsClient.viewStats(start, end, uris, true);

        Map<Integer, Integer> eventViews = new HashMap<>();

        for (ViewStatsDTO viewStatsDTO : viewStatsDTOS) {
            int index = viewStatsDTO.getUri().lastIndexOf('/') + 1;
            int id = parseInt(viewStatsDTO.getUri().substring(index));
            eventViews.put(id, viewStatsDTO.getHits());
        }

        return eventViews;
    }

    private Specification<Event> eventSpecification(EventPublicParams params) {
        Specification<Event> spec = Specification.where(null);

        if (Objects.nonNull(params.getText()) && !params.getText().isBlank()) {
            String search = "%" + params.getText().toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("annotation")), search),
                            cb.like(cb.lower(root.get("description")), search)
                    ));
        }

        if (Objects.nonNull(params.getCategories()) && !params.getCategories().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    root.get("category").get("id").in(
                    params.getCategories()));
        }

        if (Objects.nonNull(params.getPaid())) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("paid"), params.getPaid()));
        }

        if (Objects.nonNull(params.getOnlyAvailable())) {
            spec = spec.and((root, query, cb) ->
                cb.or(
                        cb.equal(root.get("participantLimit"), 0),
                        cb.lessThan(
                                root.get("confirmedRequests"),
                                root.get("participantLimit")
                        )
                ));
        }

        if (Objects.nonNull(params.getRangeStart())) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("eventDate"), params.getRangeStart()));
        }

        if (Objects.nonNull(params.getRangeEnd())) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("eventDate"), params.getRangeEnd()));
        }

        return spec;
    }
}
