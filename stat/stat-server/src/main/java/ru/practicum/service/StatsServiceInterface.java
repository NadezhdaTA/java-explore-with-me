package ru.practicum.service;

import ru.practicum.EndpointHitDTO;
import ru.practicum.StatsRequestDTO;
import ru.practicum.ViewStatsDTO;

import java.util.List;

public interface StatsServiceInterface {
    List<ViewStatsDTO> getStats(StatsRequestDTO statsRequestDTO);

    void createHit(EndpointHitDTO hitDTO);
}
