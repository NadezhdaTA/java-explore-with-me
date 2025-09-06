package ru.practicum.User.DTO;

import java.util.List;

public record UsersListRequest(
        List<Integer> ids,
        Integer from,
        Integer size) {
}
