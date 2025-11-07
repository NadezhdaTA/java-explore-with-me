package ru.practicum.compilation.service.admin;

import ru.practicum.compilation.compilationDTO.CompilationDTO;
import ru.practicum.compilation.compilationDTO.NewCompilationDTO;
import ru.practicum.compilation.compilationDTO.UpdateCompilationRequest;


public interface CompilationAdminService {
    CompilationDTO addCompilation(NewCompilationDTO compilationDTO);

    void deleteCompilation(Integer compId);

    CompilationDTO updateCompilation(Integer compId, UpdateCompilationRequest compilationDTO);
}
