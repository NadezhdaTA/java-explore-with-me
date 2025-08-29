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
import ru.practicum.Exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationPublicServiceImpl implements CompilationPublicService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDTO getCompilation(Integer compId) {
        return compilationMapper.toDto(checkCompilation(compId));
    }

    @Override
    public List<CompilationDTO> getCompilations(CompilationsListRequestParams params) {
        Pageable pageable = PageRequest.of(params.getFrom(), params.getSize());
        Page<Compilation> compilations = compilationRepository.findCompilationByPinned(params.getPinned(), pageable);
        return compilations.getContent().stream()
                .map(compilationMapper::toDto)
                .toList();
    }

    private Compilation checkCompilation(Integer compId) {
        return compilationRepository.findCompilationById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id " + compId + " not found"));
    }
}
