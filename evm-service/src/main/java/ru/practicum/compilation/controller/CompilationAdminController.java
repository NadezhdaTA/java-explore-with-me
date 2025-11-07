package ru.practicum.compilation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.compilationDTO.CompilationDTO;
import ru.practicum.compilation.compilationDTO.NewCompilationDTO;
import ru.practicum.compilation.compilationDTO.UpdateCompilationRequest;
import ru.practicum.compilation.service.admin.CompilationAdminService;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationAdminController {
    private final CompilationAdminService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDTO addCompilation(@RequestBody @Valid NewCompilationDTO compilationDTO) {
        log.info("Adding new compilation {}", compilationDTO);
        return compilationService.addCompilation(compilationDTO);

    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Integer compId) {
        log.info("Deleting compilation {}", compId);
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDTO updateCompilation(@PathVariable Integer compId,
                                            @RequestBody @Valid UpdateCompilationRequest compilationDTO) {
        log.info("Updating compilation {}, {}", compId, compilationDTO);
        return compilationService.updateCompilation(compId, compilationDTO);
    }

}
