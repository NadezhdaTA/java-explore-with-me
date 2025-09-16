package ru.practicum.comment.service.close;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentDTO;
import ru.practicum.comment.dto.NewCommentDTO;
import ru.practicum.comment.dto.UpdateCommentDTO;
import ru.practicum.comment.mapper.CommentsMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.model.Status;
import ru.practicum.comment.repository.CommentsRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentPrivateServiceImpl implements CommentPrivateService {
    private final CommentsRepository commentsRepository;
    private final CommentsMapper commentsMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Override
    public CommentDTO addComment(NewCommentDTO commentDTO, Integer userId, Integer eventId) {
        User user = checkUser(userId);
        Event event = checkEvent(eventId);

        if (event.getEventDate().isBefore(commentDTO.getCreatedAt())) {
            throw new ValidationException("Event date is before comment date");
        }

        Request request;
        try {
            request = requestRepository.findRequestsByEvent_IdAndRequester_Id(eventId, userId);
        } catch (RuntimeException e) {
            throw new NotFoundException("Request for user with id " + userId +
                    " and event with id " + eventId + " not found");
        }

        if (!request.getStatus().equals(State.CONFIRMED)) {
            throw new ValidationException("Request is not confirmed");
        }

        Comment comment = commentsMapper.toComment(commentDTO);
        comment.setStatus(Status.CREATED);
        comment.setAuthor(user);
        comment.setEvent(event);

        CommentDTO dto = commentsMapper.toCommentDTO(commentsRepository.save(comment));
        log.info("Added comment: {}", dto);

        return dto;
    }

    @Override
    public CommentDTO getComment(Integer commentId) {
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));

        CommentDTO dto;
        if (comment.getStatus().equals(Status.CREATED)) {
            dto = commentsMapper.toCommentDTO(comment);
        } else {
            dto = commentsMapper.toUpdatedCommentDTO(comment);
        }

        log.info("Found comment: {}", dto);
        return dto;
    }

    @Override
    public CommentDTO updateComment(UpdateCommentDTO commentDTO, Integer userId, Integer commentId) {
        User user = checkUser(userId);
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));

        if (!Objects.equals(comment.getAuthor().getId(), user.getId())) {
            throw new ValidationException("You are not allowed to update this comment");
        }

        comment.setComment(commentDTO.getComment());
        comment.setStatus(Status.UPDATED);
        comment.setUpdatedAt(commentDTO.getUpdatedAt());

        CommentDTO dto = commentsMapper.toUpdatedCommentDTO(commentsRepository.save(comment));
        log.info("Updated comment: {}", dto);

        return dto;
    }

    @Override
    public void deleteComment(Integer commentId, Integer userId) {
        User user = checkUser(userId);
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));
        if (!Objects.equals(comment.getAuthor().getId(), user.getId())) {
            throw new ValidationException("You are not allowed to delete this comment");
        }
        commentsRepository.deleteById(commentId);
        log.info("Comment with id = {} is deleted", commentId);
    }


    private User checkUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    private Event checkEvent(Integer id) {
        return eventRepository.findEventById(id)
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " not found"));
    }
}
