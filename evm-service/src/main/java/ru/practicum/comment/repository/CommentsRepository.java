package ru.practicum.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comment.model.Comment;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comment, Integer> {
    Comment save(Comment comment);

    List<Comment> findCommentsByEvent_Id(Integer eventId, Pageable pageable);

    Optional<Comment> findById(Integer commentId);

    Comment findCommentByEvent_IdAndAuthor_Id(Integer eventId, Integer authorId);

    Optional<Comment> findCommentById(Integer commentId);
}
