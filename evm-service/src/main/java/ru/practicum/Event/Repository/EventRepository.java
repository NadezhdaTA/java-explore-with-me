package ru.practicum.Event.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.Event.Model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer>, QuerydslPredicateExecutor<Event> {
    Optional<Event> findEventById(Integer eventId);

    List<Event> findEventsByInitiatorId(Integer initiator_id);

    Page<Event> getEventByInitiator_Id(Integer initiatorId, Pageable pageable);

    List<Event> findEventsByIdIn(List<Integer> ids);
}