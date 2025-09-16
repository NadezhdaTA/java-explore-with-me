package ru.practicum.compilation.service.open;

import ru.practicum.compilation.compilationDTO.CompilationDTO;
import ru.practicum.compilation.compilationDTO.CompilationsListRequestParams;

import java.util.List;

public interface CompilationPublicService {
    CompilationDTO getCompilation(Integer compId);

    List<CompilationDTO> getCompilations(CompilationsListRequestParams params);
}
