package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findEventById(Integer eventId);

    Optional<Event> findEventsByIdAndState(Integer id, State state);

    Page<Event> getEventByInitiator_Id(Integer initiatorId, Pageable pageable);

    List<Event> findEventsByIdIn(List<Integer> ids);

    List<Event> findEventsByCategory_Id(Integer categoryId);

    Page<Event> findAll(Specification<Event> spec, Pageable pageable);

}