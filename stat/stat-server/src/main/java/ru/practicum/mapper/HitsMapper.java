package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.EndpointHitDTO;
import ru.practicum.model.EndpointHit;

@Mapper(componentModel = "spring", uses = {EndpointHit.class})
public interface HitsMapper {

    EndpointHit mapHitDTO(EndpointHitDTO endpointHitDTO);

}
