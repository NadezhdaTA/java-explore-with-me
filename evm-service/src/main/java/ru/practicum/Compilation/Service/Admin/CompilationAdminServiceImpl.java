package ru.practicum.Compilation.Service.Admin;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Compilation.CompilationDTO.CompilationDTO;
import ru.practicum.Compilation.CompilationDTO.NewCompilationDTO;
import ru.practicum.Compilation.CompilationDTO.UpdateCompilationRequest;
import ru.practicum.Compilation.Mapper.CompilationMapper;
import ru.practicum.Compilation.Model.Compilation;
import ru.practicum.Compilation.Repository.CompilationRepository;
import ru.practicum.Event.Mapper.EventMapper;
import ru.practicum.Event.Model.Event;
import ru.practicum.Event.Repository.EventRepository;
import ru.practicum.Exception.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
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

        return compilationMapper.toDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Integer compId) {
        compilationRepository.delete(checkCompilation(compId));
    }

    @Override
    public CompilationDTO updateCompilation(Integer compId, UpdateCompilationRequest compilationDTO) {
        Compilation compilation = checkCompilation(compId);

        if (compilationDTO.getEvents() != null) {
            Set<Event> events = setEvents(compilationDTO.getEvents());
            compilation.setEvents(events);
        }

        return compilationMapper.toDto(compilationRepository.save(compilation));
    }

    private Compilation checkCompilation(Integer compId) {
        return compilationRepository.findCompilationById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id " + compId + " not found"));
    }

    private Set<Event> setEvents(List<Integer> eventIds) {
        return new HashSet<>(eventRepository.findEventsByIdIn(eventIds));
    }
}
