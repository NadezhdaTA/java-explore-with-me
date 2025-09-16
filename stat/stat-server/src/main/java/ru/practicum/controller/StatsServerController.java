package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.EndpointHitDTO;
import ru.practicum.StatsRequestDTO;
import ru.practicum.ViewStatsDTO;
import ru.practicum.service.StatsServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsServerController {
    private final StatsServiceImpl statsService;

    @GetMapping("/stats")
    private List<ViewStatsDTO> viewStats(@ModelAttribute @Valid StatsRequestDTO statsRequestDTO) {
        log.info("Getting view stats");
        return statsService.getStats(statsRequestDTO);
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    private void createHit(@RequestBody @Valid EndpointHitDTO hitDTO) {
        log.info("Creating hit {}", hitDTO);
        statsService.createHit(hitDTO);
    }
}
