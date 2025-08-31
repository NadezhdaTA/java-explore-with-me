package ru.practicum.Request.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Event.Model.Event;
import ru.practicum.Event.Model.State;
import ru.practicum.Event.Repository.EventRepository;
import ru.practicum.Exception.ConflictException;
import ru.practicum.Exception.NotFoundException;
import ru.practicum.Request.Mapper.RequestMapper;
import ru.practicum.Request.Model.Request;
import ru.practicum.Request.Repository.RequestRepository;
import ru.practicum.Request.RequestDTO.ParticipationRequestDto;
import ru.practicum.User.Model.User;
import ru.practicum.User.Repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestPrivateServiceImpl implements RequestPrivateService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    @Override
    public ParticipationRequestDto addRequest(Integer userId, Integer eventId) {
        Request request = new Request();

        Event event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        User user = checkUser(userId);

        if (requestRepository.findRequestsByEvent_IdAndRequester_Id(eventId, userId) != null) {
            throw new ConflictException("Request already exists");
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Event is not published");
        } else if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Requester and initiator could not be the same.");
        }

        if (event.getParticipantLimit().equals(event.getConfirmedRequests()) && event.getParticipantLimit() != 0) {
            throw new ConflictException("Request limit exceeded");
        }

        if (!(event.getRequestModeration()) || (event.getParticipantLimit() == 0)) {
            request.setStatus(State.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }  else {
            request.setStatus(State.PENDING);
        }

        request.setEvent(event);
        request.setRequester(user);

        return requestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        Request request = requestRepository.findRequestById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with id " + requestId + " not found"));

        checkUser(userId);

        request.setStatus(State.CANCELED);

        return requestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Integer userId) {
        checkUser(userId);
        return requestRepository.findRequestsByRequester_Id(userId).stream()
                .map(requestMapper::toParticipationRequestDto)
                .toList();
    }

    private User checkUser(Integer userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }
}
