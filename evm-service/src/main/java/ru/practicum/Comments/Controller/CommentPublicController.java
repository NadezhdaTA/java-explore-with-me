package ru.practicum.Comments.Controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Comments.DTO.CommentDTO;
import ru.practicum.Comments.Service.Public.CommentPublicInterface;

import java.util.List;

@RestController
@RequestMapping("/event/{eventId}/comments")
@RequiredArgsConstructor
public class CommentPublicController {
    private final CommentPublicInterface commentPublicInterface;

    @GetMapping
    public List<CommentDTO> getComments(@PathVariable @NotNull Integer eventId,
                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(defaultValue = "10") @Positive Integer size) {
        return commentPublicInterface.getComments(eventId, from, size);
    }
}
