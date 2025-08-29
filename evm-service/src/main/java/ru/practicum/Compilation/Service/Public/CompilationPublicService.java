package ru.practicum.Compilation.Service.Public;

import ru.practicum.Compilation.CompilationDTO.CompilationDTO;
import ru.practicum.Compilation.CompilationDTO.CompilationsListRequestParams;

import java.util.List;

public interface CompilationPublicService {
    CompilationDTO getCompilation(Integer compId);

    List<CompilationDTO> getCompilations(CompilationsListRequestParams params);
}
