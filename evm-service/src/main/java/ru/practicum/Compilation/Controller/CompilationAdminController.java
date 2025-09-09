package ru.practicum.Compilation.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Compilation.CompilationDTO.CompilationDTO;
import ru.practicum.Compilation.CompilationDTO.NewCompilationDTO;
import ru.practicum.Compilation.CompilationDTO.UpdateCompilationRequest;
import ru.practicum.Compilation.Service.Admin.CompilationAdminServiceImpl;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {
    private final CompilationAdminServiceImpl compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDTO addCompilation(@RequestBody @Valid NewCompilationDTO compilationDTO) {
        return compilationService.addCompilation(compilationDTO);

    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Integer compId) {
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDTO updateCompilation(@PathVariable Integer compId,
                                            @RequestBody @Valid UpdateCompilationRequest compilationDTO) {
        return compilationService.updateCompilation(compId, compilationDTO);
    }

}
