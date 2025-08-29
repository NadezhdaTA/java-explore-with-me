package ru.practicum.Compilation.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Compilation.CompilationDTO.CompilationDTO;
import ru.practicum.Compilation.CompilationDTO.CompilationsListRequestParams;
import ru.practicum.Compilation.Service.Public.CompilationPublicServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationPublicController {
    private final CompilationPublicServiceImpl compilationService;

    @GetMapping("/{compId}")
    public CompilationDTO getCompilation(@PathVariable Integer compId) {
        return compilationService.getCompilation(compId);
    }

    @GetMapping
    public List<CompilationDTO> getCompilations(@ModelAttribute CompilationsListRequestParams params) {
        return compilationService.getCompilations(params);
    }
}
