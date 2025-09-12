package ru.practicum.Comments.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Comments.DTO.CommentDTO;
import ru.practicum.Comments.DTO.NewCommentDTO;
import ru.practicum.Comments.DTO.UpdateCommentDTO;
import ru.practicum.Comments.Service.Private.CommentPrivateServiceImpl;


@RestController
@RequestMapping("/events/comments")
@RequiredArgsConstructor
public class CommentPrivateController {
    private final CommentPrivateServiceImpl commentService;
    public static final String USER_ID = "X-User-Id";

    @PostMapping("/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO addComment(@RequestBody NewCommentDTO commentDTO,
                                 @RequestHeader(USER_ID) Integer userId,
                                 @PathVariable Integer eventId) {
        return commentService.addComment(commentDTO, userId, eventId);
    }

    @GetMapping("/{commentId}")
    public CommentDTO getComment(@PathVariable Integer commentId) {
        return commentService.getComment(commentId);
    }

    @PatchMapping("/{commentId}")
    public CommentDTO updateComment(@RequestHeader(USER_ID) Integer userId,
                                    @RequestBody UpdateCommentDTO dto,
                                    @PathVariable Integer commentId) {
        return commentService.updateComment(dto, userId, commentId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@RequestHeader(USER_ID) Integer userId,
                              @PathVariable Integer commentId){
        commentService.deleteComment(commentId, userId);
    }

}
