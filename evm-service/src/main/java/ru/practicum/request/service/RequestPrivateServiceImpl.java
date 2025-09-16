package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.request.requestDTO.ParticipationRequestDto;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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

        ParticipationRequestDto participationRequestDto = requestMapper.toParticipationRequestDto(
                requestRepository.save(request));
        log.info("Request is added: {}", participationRequestDto);

        return participationRequestDto;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        Request request = requestRepository.findRequestById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with id " + requestId + " not found"));

        checkUser(userId);

        request.setStatus(State.CANCELED);

        ParticipationRequestDto participationRequestDto = requestMapper.toParticipationRequestDto(
                requestRepository.save(request));
        log.info("Request is cancelled: {}", participationRequestDto);

        return participationRequestDto;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Integer userId) {
        checkUser(userId);
        log.info("Found requests for user with id = {} are sent", userId);
        return requestRepository.findRequestsByRequester_Id(userId).stream()
                .map(requestMapper::toParticipationRequestDto)
                .toList();
    }

    private User checkUser(Integer userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }
}
