package ru.practicum.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDTO;
import ru.practicum.user.dto.UsersListRequest;
import ru.practicum.user.service.UserAdminService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class UserAdminController {
    private final UserAdminService userAdminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addUser(@RequestBody @Valid NewUserRequest newUser) {
        log.info("Adding new user {}", newUser);
        return userAdminService.addUser(newUser);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable Integer userId) {
        log.info("Getting user with id = {}", userId);
        return userAdminService.getUserById(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers(@RequestParam(required = false) List<Integer> ids,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Getting users with ids {}, from {}, size {}", ids, from, size);
        return userAdminService.getUsers(new UsersListRequest(ids, from, size));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer userId) {
        log.info("Deleting user with id = {}", userId);
        userAdminService.deleteUserById(userId);
    }
}
