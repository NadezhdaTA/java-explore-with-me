package ru.practicum.compilation.service.admin;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.compilationDTO.CompilationDTO;
import ru.practicum.compilation.compilationDTO.NewCompilationDTO;
import ru.practicum.compilation.compilationDTO.UpdateCompilationRequest;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationAdminServiceImpl implements CompilationAdminService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;
    private final EventMapper eventMapper;

    @Override
    public CompilationDTO addCompilation(NewCompilationDTO compilationDTO) {
        Compilation compilation = compilationMapper.toCompilation(compilationDTO);

        if (compilationDTO.getEvents() != null) {
            Set<Event> events = setEvents(compilationDTO.getEvents());
            compilation.setEvents(events);
        }

        CompilationDTO dto = compilationMapper.toDto(compilationRepository.save(compilation));
        log.info("Compilation is added: {}", dto);
        return dto;
    }

    @Override
    public void deleteCompilation(Integer compId) {
        compilationRepository.delete(checkCompilation(compId));
        log.info("Compilation with id = {} is deleted", compId);
    }

    @Override
    public CompilationDTO updateCompilation(Integer compId, UpdateCompilationRequest compilationDTO) {
        Compilation compilation = checkCompilation(compId);

        if (compilationDTO.getEvents() != null) {
            Set<Event> events = setEvents(compilationDTO.getEvents());
            compilation.setEvents(events);
        }

        CompilationDTO dto = compilationMapper.toDto(compilationRepository.save(compilation));
        log.info("Compilation with id = {} is updated: {}", compId, dto);
        return dto;
    }

    private Compilation checkCompilation(Integer compId) {
        return compilationRepository.findCompilationById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id " + compId + " not found"));
    }

    private Set<Event> setEvents(List<Integer> eventIds) {
        return new HashSet<>(eventRepository.findEventsByIdIn(eventIds));
    }
}
