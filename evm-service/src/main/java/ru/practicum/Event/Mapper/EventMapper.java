package ru.practicum.Event.Mapper;

import org.mapstruct.*;
import ru.practicum.Event.DTO.*;
import ru.practicum.Event.Model.Event;

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
