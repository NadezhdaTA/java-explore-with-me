package ru.practicum.Comments.Service.Public;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.Comments.DTO.CommentDTO;
import ru.practicum.Comments.Mapper.CommentsMapper;
import ru.practicum.Comments.Repository.CommentsRepository;
import ru.practicum.Event.Model.Event;
import ru.practicum.Event.Repository.EventRepository;
import ru.practicum.Exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentPublicInterfaceImpl implements CommentPublicInterface {
    private final CommentsRepository commentsRepository;
    private final CommentsMapper commentsMapper;
    private final EventRepository eventRepository;

    @Override
    public List<CommentDTO> getComments(Integer eventId, Integer from, Integer size) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id " + eventId + " not found"));

        Pageable pageable = PageRequest.of(from, size);

        return commentsRepository.findCommentsByEvent_Id(eventId, pageable).stream()
                .map(commentsMapper::toCommentDTO)
                .toList();
    }
}
