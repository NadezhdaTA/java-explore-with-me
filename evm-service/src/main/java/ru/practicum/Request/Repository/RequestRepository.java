package ru.practicum.Request.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.Request.Model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    Optional<Request> findRequestById(Integer requestId);

    List<Request> findRequestsByRequester_Id(Integer requesterId);
}
