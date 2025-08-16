package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.ViewStatsDTO;
import ru.practicum.model.ViewStats;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ViewStatsMapper {
    ViewStats mapViewStatsDTO(ViewStatsDTO viewStatsDTO);

    ViewStatsDTO mapViewStats(ViewStats viewStats);
}
