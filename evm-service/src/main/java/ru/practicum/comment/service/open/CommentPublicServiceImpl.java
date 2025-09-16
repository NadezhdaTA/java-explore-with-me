package ru.practicum.comment.service.open;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentDTO;
import ru.practicum.comment.mapper.CommentsMapper;
import ru.practicum.comment.repository.CommentsRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentPublicServiceImpl implements CommentPublicService {
    private final CommentsRepository commentsRepository;
    private final CommentsMapper commentsMapper;
    private final EventRepository eventRepository;

    @Override
    public List<CommentDTO> getComments(Integer eventId, Integer from, Integer size) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id " + eventId + " not found"));

        Pageable pageable = PageRequest.of(from, size);

        log.info("Found comments to event with id = {} are sent (from = {}, size = {})", eventId, from, size);
        return commentsRepository.findCommentsByEvent_Id(eventId, pageable).stream()
                .map(commentsMapper::toCommentDTO)
                .toList();
    }
}
