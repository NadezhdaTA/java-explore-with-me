package ru.practicum.Event.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.Event.Model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findEventById(Integer eventId);


    List<Event> findEventsByInitiatorId(Integer initiator_id);
}
