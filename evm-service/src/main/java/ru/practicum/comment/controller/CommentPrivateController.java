package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDTO;
import ru.practicum.comment.dto.NewCommentDTO;
import ru.practicum.comment.dto.UpdateCommentDTO;
import ru.practicum.comment.service.close.CommentPrivateService;


@RestController
@RequestMapping("/events/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentPrivateController {
    private final CommentPrivateService commentService;
    public static final String USER_ID = "X-User-Id";

    @PostMapping("/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO addComment(@RequestBody NewCommentDTO commentDTO,
                                 @RequestHeader(USER_ID) Integer userId,
                                 @PathVariable Integer eventId) {
        log.info("Adding comment to event with id = {}, userId = {}, " +
                "comment : {}", eventId, userId, commentDTO);
        return commentService.addComment(commentDTO, userId, eventId);
    }

    @GetMapping("/{commentId}")
    public CommentDTO getComment(@PathVariable Integer commentId) {
        log.info("Getting comment by id: {}", commentId);
        return commentService.getComment(commentId);
    }

    @PatchMapping("/{commentId}")
    public CommentDTO updateComment(@RequestHeader(USER_ID) Integer userId,
                                    @RequestBody UpdateCommentDTO dto,
                                    @PathVariable Integer commentId) {
        log.info("Updating comment with id = {}, userId = {},  comment: {}", commentId, userId, dto);
        return commentService.updateComment(dto, userId, commentId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@RequestHeader(USER_ID) Integer userId,
                              @PathVariable Integer commentId) {
        log.info("Deleting comment with id = {} by user with id = {}", commentId, userId);
        commentService.deleteComment(commentId, userId);
    }

}
