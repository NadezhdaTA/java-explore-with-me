package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.EndpointHitDTO;
import ru.practicum.StatsRequestDTO;
import ru.practicum.ViewStatsDTO;
import ru.practicum.service.StatsServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsServerController {
    private final StatsServiceImpl statsService;

    @GetMapping("/stats")
    private List<ViewStatsDTO> viewStats(@ModelAttribute @Valid StatsRequestDTO statsRequestDTO) {
        return statsService.getStats(statsRequestDTO);
    }

    @PostMapping("/hit")
    private void createHit(@RequestBody @Valid EndpointHitDTO hitDTO) {
        statsService.createHit(hitDTO);
    }
}
