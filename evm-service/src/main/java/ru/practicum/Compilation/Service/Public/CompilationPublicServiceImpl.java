package ru.practicum.Compilation.Service.Public;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.Compilation.CompilationDTO.CompilationDTO;
import ru.practicum.Compilation.CompilationDTO.CompilationsListRequestParams;
import ru.practicum.Compilation.Mapper.CompilationMapper;
import ru.practicum.Compilation.Model.Compilation;
import ru.practicum.Compilation.Repository.CompilationRepository;
import ru.practicum.Event.Mapper.EventMapper;
import ru.practicum.Event.Model.Event;
import ru.practicum.Event.Repository.EventRepository;
import ru.practicum.Exception.NotFoundException;
import ru.practicum.StatsClient;
import ru.practicum.ViewStatsDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Service
@RequiredArgsConstructor
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

        return compilationDTO;
    }

    @Override
    public List<CompilationDTO> getCompilations(CompilationsListRequestParams params) {
        Pageable pageable = PageRequest.of(params.getFrom(), params.getSize());
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

        return compilationDtos;

    }

    private Compilation checkCompilation(Integer compId) {
        return compilationRepository.findCompilationById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id " + compId + " not found"));
    }

    private Map<Integer, Integer> getEvents(List<Event> events) {
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

