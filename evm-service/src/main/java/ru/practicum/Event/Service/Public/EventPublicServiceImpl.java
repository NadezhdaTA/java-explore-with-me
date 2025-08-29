package ru.practicum.Event.Service.Public;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Category.Model.Category;
import ru.practicum.Category.Repository.CategoryRepository;
import ru.practicum.Event.DTO.EventFullDTO;
import ru.practicum.Event.DTO.EventPublicParams;
import ru.practicum.Event.DTO.EventShortDTO;
import ru.practicum.Event.Mapper.EventMapper;
import ru.practicum.Event.Model.Event;
import ru.practicum.Event.Model.QEvent;
import ru.practicum.Event.Repository.EventRepository;
import ru.practicum.Exception.NotFoundException;
import ru.practicum.Request.Model.QRequest;
import ru.practicum.Request.Repository.RequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;

    @Override
    public EventFullDTO findEventById(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " not found"));
        return eventMapper.toEventFullDTO(event);
    }

    @Override
    public List<EventShortDTO> findAllEvents(EventPublicParams params) {
        List<Category> categories = categoryRepository.findAllById(params.getCategories());


        BooleanExpression byPaid = QEvent.event.paid.eq(params.getPaid());

      /* List<Event> events = eventRepository.findAll(byPaid
                .and(QEvent.event.description.contains(params.getText()))
                .and(QEvent.event.eventDate.between(params.getRangeStart(), params.getRangeEnd()))
                .and(QEvent.event.category.in(categories))
                .and(QEvent.event.participantLimit)
                .orderedBy(params.getSort())
                .pageble(params.getFrom(), params.getSize())
                );*/
        return List.of();
    }
}
