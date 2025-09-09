package ru.practicum.Compilation.Service.Admin;

import ru.practicum.Compilation.CompilationDTO.CompilationDTO;
import ru.practicum.Compilation.CompilationDTO.NewCompilationDTO;
import ru.practicum.Compilation.CompilationDTO.UpdateCompilationRequest;


public interface CompilationAdminService {
    CompilationDTO addCompilation(NewCompilationDTO compilationDTO);

    void deleteCompilation(Integer compId);

    CompilationDTO updateCompilation(Integer compId, UpdateCompilationRequest compilationDTO);
}
