package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.State;
import ru.practicum.request.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    Optional<Request> findRequestById(Integer requestId);

    List<Request> findRequestsByRequester_Id(Integer requesterId);

    List<Request> findRequestsByEvent_IdAndEvent_Initiator_Id(Integer eventId, Integer eventInitiatorId);

    List<Request> findRequestsByEvent_IdAndStatus(Integer eventId, State status);

    Request findRequestsByEvent_IdAndRequester_Id(Integer eventId, Integer requesterId);
}
