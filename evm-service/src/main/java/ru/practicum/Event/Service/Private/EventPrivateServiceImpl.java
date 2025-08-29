package ru.practicum.Event.Service.Private;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.Category.Model.Category;
import ru.practicum.Category.Repository.CategoryRepository;
import ru.practicum.Event.DTO.*;
import ru.practicum.Event.Mapper.EventMapper;
import ru.practicum.Event.Model.Event;
import ru.practicum.Event.Repository.EventRepository;
import ru.practicum.Exception.ConflictException;
import ru.practicum.Exception.NotFoundException;
import ru.practicum.Request.Mapper.RequestMapper;
import ru.practicum.Request.Model.Request;
import ru.practicum.Request.Repository.RequestRepository;
import ru.practicum.Request.RequestDTO.ParticipationRequestDto;
import ru.practicum.User.Model.User;
import ru.practicum.User.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.Event.Model.State.*;

@Service
@RequiredArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Override
    public EventFullDTO addEvent(Integer userId, NewEventDTO event) {
        User user = checkUser(userId);
        Category category = checkCategory(event.getCategoryId());

        Event newEvent = eventMapper.toEvent(event);
        checkDates(newEvent.getCreatedOn(), event.getEventDate());
        newEvent.setInitiator(user);
        newEvent.setCategory(category);
        newEvent.setState(PENDING);
        return eventMapper.toEventFullDTO(eventRepository.save(newEvent));
    }

    @Override
    public EventFullDTO getEvent(Integer userId, Integer eventId) {
        Event event = checkEvent(eventId);
        return eventMapper.toEventFullDTO(event);
    }

    @Override
    public EventFullDTO updateEvent(Integer userId, Integer eventId, UpdateEventUserRequest event) {
        Event eventFound = checkEvent(eventId);
        User user = checkUser(userId);

        if (eventFound.getState().equals(PUBLISHED)) {
            throw new ConflictException("Event with id " + eventId + " could not be changed");
        }

        if (!userId.equals(eventFound.getInitiator().getId())) {
            throw new ConflictException("Initiator id mismatch");
        }

        if (event.getCategoryId() != null) {
            Category category = checkCategory(event.getCategoryId());
            eventFound.setCategory(category);
        }

        if (event.getEventDate() != null) {
            checkDates(eventFound.getCreatedOn(), event.getEventDate());
            eventFound.setEventDate(event.getEventDate());
        }

        eventFound.setState(CANCELED);

        return eventMapper.toEventFullDTO(eventRepository.save(eventFound));
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Integer userId, Integer eventId) {
        checkUser(userId);
        checkEvent(eventId);

        return requestRepository.findRequestsByEvent_IdAndEvent_Initiator_Id(eventId, userId).stream()
                .map(requestMapper::toParticipationRequestDto)
                .toList();
    }

    @Override
    public EventRequestStatusUpdateResult updateRequests(EventRequestStatusUpdateRequest request,
                                                         Integer userId, Integer eventId) {
        Event event = checkEvent(eventId);
        checkUser(userId);
        List<Request> requests = requestRepository.findAllById(request.getRequestIds());
        List<Request> allRequests = requestRepository.findRequestsByEvent_Id(eventId);

        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();

        for (Request request1 : requests) {
            if (event.getState().equals(PUBLISHED)) {
                if (event.getParticipantLimit() != null) {

                    if (allRequests.size() < event.getParticipantLimit()) {
                        request1.setStatus(CONFIRMED);
                        requestRepository.save(request1);
                        confirmedRequests.add(requestMapper.toParticipationRequestDto(request1));
                    } else {
                        request1.setStatus(REJECTED);
                        requestRepository.save(request1);
                        confirmedRequests.add(requestMapper.toParticipationRequestDto(request1));
                        throw new ConflictException("Participation limit exceeded");
                    }
                }
            } else {
                throw new ConflictException("Event Status should be PUBLISHED");
            }
        }

        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);

    }

    @Override
    public List<EventShortDTO> getEvents(Integer userId, Integer from, Integer size) {
        checkUser(userId);
        Pageable pageable = PageRequest.of(from, size);
        Page<Event> events = eventRepository.getEventByInitiator_Id(userId, pageable);
        return events.getContent().stream()
                .map(eventMapper::toEventShortDTO)
                .toList();
    }

    private void checkDates(LocalDateTime createdOn, LocalDateTime eventDate) {
        if (eventDate.isBefore(createdOn) ||
        eventDate.minusHours(2L).isBefore(createdOn)) {
            throw new ConflictException("Event date should be 2 horse after creation  date");
        }
    }

    private User checkUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }

    private Category checkCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " not found"));
    }

    private Event checkEvent(Integer eventId) {
        return eventRepository.findEventById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id " + eventId + " not found"));
    }
}
