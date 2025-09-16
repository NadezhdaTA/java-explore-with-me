package ru.practicum.event.mapper;

import org.mapstruct.*;
import ru.practicum.event.model.Event;
import ru.practicum.event.dto.*;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEvent(NewEventDTO event);

    EventShortDTO toEventShortDTO(Event event);

    EventFullDTO toEventFullDTO(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    Event toEvent(UpdateEventAdminRequest request, @MappingTarget Event event);

    EventCommentsDTO toEventCommentsDTO(Event event);
}
