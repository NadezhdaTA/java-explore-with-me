package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.compilationDTO.CompilationDTO;
import ru.practicum.compilation.compilationDTO.CompilationsListRequestParams;
import ru.practicum.compilation.service.open.CompilationPublicService;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationPublicController {
    private final CompilationPublicService compilationService;

    @GetMapping("/{compId}")
    public CompilationDTO getCompilation(@PathVariable Integer compId) {
        log.info("Getting compilation with id = {}", compId);
        return compilationService.getCompilation(compId);
    }

    @GetMapping
    public List<CompilationDTO> getCompilations(@ModelAttribute CompilationsListRequestParams params) {
        log.info("Getting compilations");
        return compilationService.getCompilations(params);
    }
}
