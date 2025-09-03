package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDTO;
import ru.practicum.StatsRequestDTO;
import ru.practicum.ViewStatsDTO;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.mapper.HitsMapper;
import ru.practicum.mapper.ViewStatsMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;
import ru.practicum.repository.StatsServerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsServiceInterface {
    private final StatsServerRepository statsService;
    private final ViewStatsMapper viewStatsMapper;
    private final HitsMapper hitsMapper;

    @Override
    public List<ViewStatsDTO> getStats(StatsRequestDTO statsRequestDTO) {
        if (statsRequestDTO.getStart().isAfter(statsRequestDTO.getEnd())) {
            throw new ValidationException("Start date cannot be after end date");
        } else if (statsRequestDTO.getStart() == null || statsRequestDTO.getEnd() == null) {
            throw new ValidationException("Start date cannot be after end date");
        }

        List<ViewStats> stats;
        if (statsRequestDTO.getUnique() == null) {
            stats = statsService.getStats(statsRequestDTO.getStart(), statsRequestDTO.getEnd()).stream()
                    .filter(viewStats -> statsRequestDTO.getUris().contains(viewStats.getUri()))
                    .toList();
        } else if (statsRequestDTO.getUnique().equals(true)) {
                stats = statsService.getStatsUnique(statsRequestDTO.getStart(), statsRequestDTO.getEnd(),
                        statsRequestDTO.getUris());
        } else {
            stats = statsService.getStats(statsRequestDTO.getStart(), statsRequestDTO.getEnd());
        }
        List<ViewStatsDTO> views =  stats.stream()
                .map(viewStatsMapper::mapViewStats)
                .toList();

        return views;
    }

    @Override
    public void createHit(EndpointHitDTO hitDTO) {
        EndpointHit hit = hitsMapper.mapHitDTO(hitDTO);
        statsService.createHit(hit);
    }
}
