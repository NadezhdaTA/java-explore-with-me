package ru.practicum.Compilation.Service.Admin;

import ru.practicum.Compilation.CompilationDTO.CompilationDTO;
import ru.practicum.Compilation.CompilationDTO.NewCompilationDTO;


public interface CompilationAdminService {
    CompilationDTO addCompilation(NewCompilationDTO compilationDTO);

    void deleteCompilation(Integer compId);

    CompilationDTO updateCompilation(Integer compId, NewCompilationDTO compilationDTO);
}
