package ru.practicum.Comments.Service.Private;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Comments.DTO.CommentDTO;
import ru.practicum.Comments.DTO.NewCommentDTO;
import ru.practicum.Comments.DTO.UpdateCommentDTO;
import ru.practicum.Comments.Mapper.CommentsMapper;
import ru.practicum.Comments.Model.Comment;
import ru.practicum.Comments.Model.Status;
import ru.practicum.Comments.Repository.CommentsRepository;
import ru.practicum.Event.Model.Event;
import ru.practicum.Event.Model.State;
import ru.practicum.Event.Repository.EventRepository;
import ru.practicum.Exception.NotFoundException;
import ru.practicum.Exception.ValidationException;
import ru.practicum.Request.Model.Request;
import ru.practicum.Request.Repository.RequestRepository;
import ru.practicum.User.Model.User;
import ru.practicum.User.Repository.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
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
        } catch (NotFoundException e) {
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

        return commentsMapper.toCommentDTO(commentsRepository.save(comment));
    }

    @Override
    public CommentDTO getComment(Integer commentId) {
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));

        if (comment.getStatus().equals(Status.CREATED)) {
            return commentsMapper.toCommentDTO(comment);
        } else {
            return commentsMapper.toUpdatedCommentDTO(comment);
        }
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

        return commentsMapper.toUpdatedCommentDTO(commentsRepository.save(comment));
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
