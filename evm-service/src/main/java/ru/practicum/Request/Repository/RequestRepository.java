package ru.practicum.Request.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.Request.Model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer>, JpaSpecificationExecutor<Request> {
    Optional<Request> findRequestById(Integer requestId);

    List<Request> findRequestsByRequester_Id(Integer requesterId);

    List<Request> findRequestsByEvent_IdAndEvent_Initiator_Id(Integer eventId, Integer eventInitiatorId);

    List<Request> findRequestsByEvent_Id(Integer eventId);
}
