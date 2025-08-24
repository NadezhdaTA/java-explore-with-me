package ru.practicum.User.DTO;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Set;

@Data
public class UsersListRequest {
    private Set<Integer> ids;

    @PositiveOrZero
    private final Integer from = 0;

    @Positive
    private final Integer size = 10;
}
