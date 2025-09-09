package ru.practicum.Request.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.Request.Model.Request;
import ru.practicum.Request.RequestDTO.ParticipationRequestDto;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    ParticipationRequestDto toParticipationRequestDto(Request request);

    @Mapping(target = "event", ignore = true)
    @Mapping(target = "requester", ignore = true)
    Request toRequest(ParticipationRequestDto participationRequestDto);
}
