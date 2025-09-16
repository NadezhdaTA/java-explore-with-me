package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDTO;
import ru.practicum.StatsRequestDTO;
import ru.practicum.ViewStatsDTO;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.mapper.HitsMapper;
import ru.practicum.mapper.ViewStatsMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.StatsServerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsServerRepository statsService;
    private final ViewStatsMapper viewStatsMapper;
    private final HitsMapper hitsMapper;

    @Override
    public List<ViewStatsDTO> getStats(StatsRequestDTO statsRequestDTO) {
        if (statsRequestDTO.getStart().isAfter(statsRequestDTO.getEnd())) {
            throw new ValidationException("Start date cannot be after end date");
        }

        List<ViewStatsDTO> stats = new ArrayList<>();
        if (statsRequestDTO.getUnique() != null && statsRequestDTO.getUnique()) {
            stats = statsService.getStatsUnique(statsRequestDTO.getStart(), statsRequestDTO.getEnd(),
                                statsRequestDTO.getUris()).stream()
                        .map(viewStatsMapper::mapViewStats)
                        .toList();
        }

        if (statsRequestDTO.getUnique() == null || statsRequestDTO.getUnique().equals(false)) {
            stats = statsService.getStats(statsRequestDTO.getStart(), statsRequestDTO.getEnd()).stream()
                    .filter(viewStats -> statsRequestDTO.getUris().contains(viewStats.getUri()))
                    .map(viewStatsMapper::mapViewStats)
                    .toList();

        }
        log.info("Found {} stats", stats.size());
        return stats;
    }

    @Override
    public void createHit(EndpointHitDTO hitDTO) {
        EndpointHit hit = hitsMapper.mapHitDTO(hitDTO);
        statsService.createHit(hit);
        log.info("Created hit {}", hit);
    }
}
