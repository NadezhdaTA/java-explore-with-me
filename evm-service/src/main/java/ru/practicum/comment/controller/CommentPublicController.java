package ru.practicum.comment.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDTO;
import ru.practicum.comment.service.open.CommentPublicService;

import java.util.List;

@RestController
@RequestMapping("/event/{eventId}/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentPublicController {
    private final CommentPublicService commentPublicInterface;

    @GetMapping
    public List<CommentDTO> getComments(@PathVariable @NotNull Integer eventId,
                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Getting comments to event with id = {}, from - {}, size - {}", eventId, from, size);
        return commentPublicInterface.getComments(eventId, from, size);
    }
}
