package ru.practicum.User.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.User.DTO.NewUserRequest;
import ru.practicum.User.DTO.UserDTO;
import ru.practicum.User.DTO.UsersListRequest;
import ru.practicum.User.Service.UserAdminServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserAdminServiceImpl userAdminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addUser(@RequestBody @Valid NewUserRequest newUser) {
        return userAdminService.addUser(newUser);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable Integer userId) {
        return userAdminService.getUserById(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers(@ModelAttribute UsersListRequest request) {
        return userAdminService.getUsers(request);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer userId) {
        userAdminService.deleteUserById(userId);
    }
}
