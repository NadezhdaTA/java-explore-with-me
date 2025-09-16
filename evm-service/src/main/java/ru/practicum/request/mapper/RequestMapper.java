package ru.practicum.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.request.model.Request;
import ru.practicum.request.requestDTO.ParticipationRequestDto;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    ParticipationRequestDto toParticipationRequestDto(Request request);

    @Mapping(target = "event", ignore = true)
    @Mapping(target = "requester", ignore = true)
    Request toRequest(ParticipationRequestDto participationRequestDto);
}
