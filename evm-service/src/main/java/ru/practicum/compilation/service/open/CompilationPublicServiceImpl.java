package ru.practicum.compilation.service.open;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.compilationDTO.CompilationDTO;
import ru.practicum.compilation.compilationDTO.CompilationsListRequestParams;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.StatsClient;
import ru.practicum.ViewStatsDTO;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Integer.parseInt;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationPublicServiceImpl implements CompilationPublicService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;
    private final StatsClient statsClient;
    private final EventMapper eventMapper;

    @Override
    public CompilationDTO getCompilation(Integer compId) {
        Compilation compilation = checkCompilation(compId);
        CompilationDTO compilationDTO = compilationMapper.toDto(compilation);

        List<Event> events = compilation.getEvents().stream().toList();

        if ((events != null) && (!events.isEmpty())) {
            Map<Integer, Integer> views = getEvents(events);
            compilationDTO.getEvents().stream()
                    .peek(eventShortDTO -> eventShortDTO.setViews(views.get(eventShortDTO.getId())));
        }

        log.info("Compilation with id = {} is found: {}", compId, compilationDTO);
        return compilationDTO;
    }

    @Override
    public List<CompilationDTO> getCompilations(CompilationsListRequestParams params) {
        int page = (int) Math.floor((double) params.getFrom() / params.getSize());
        Pageable pageable = PageRequest.of(page, params.getSize());
        Page<Compilation> compilations = params.getPinned() == null
                ? compilationRepository.findAll(pageable)
                : compilationRepository.findCompilationByPinned(params.getPinned(), pageable);

        List<Event> events = compilations.stream()
                .flatMap(c -> c.getEvents().stream())
                .distinct()
                .toList();

        List<CompilationDTO> compilationDtos = compilations.getContent().stream()
                .map(compilationMapper::toDto)
                .toList();


        if ((events != null) && (!events.isEmpty())) {
            Map<Integer, Integer> views = getEvents(events);
            compilationDtos = compilationDtos.stream().peek(compilationDTO -> {
                        compilationDTO.getEvents().stream()
                                .peek(eventShortDTO -> eventShortDTO.setViews(views.get(eventShortDTO.getId())
                                ));
                    }
            ).toList();
        }
        log.info("Found compilations are sent");
        return compilationDtos;

    }

    private Compilation checkCompilation(Integer compId) {
        return compilationRepository.findCompilationById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id " + compId + " not found"));
    }

    private Map<Integer, Integer> getEvents(List<Event> events) {
        if (events == null) {
            return Collections.emptyMap();
        }

        List<Integer> ids = events.stream()
                .map(Event::getId)
                .toList();
        List<Event> events1 = eventRepository.findEventsByIdIn(ids);

        List<String> uris = events.stream()
                .map(event -> "/events/" + event.getId())
                .distinct()
                .toList();
        LocalDateTime start = LocalDateTime.now().minusMonths(6);
        LocalDateTime end = LocalDateTime.now().plusMinutes(1);
        List<ViewStatsDTO> viewStatsDTOS = statsClient.viewStats(start, end, uris, true);

        Map<Integer, Integer> views = new HashMap<>();
        if (!viewStatsDTOS.isEmpty()) {
            for (ViewStatsDTO viewStatsDTO : viewStatsDTOS) {
                int eventId = parseInt(viewStatsDTO.getUri().substring("/events/".length()));
                views.put(eventId, viewStatsDTO.getHits());
            }
        }

        return views;
    }
}

