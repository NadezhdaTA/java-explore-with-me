package ru.practicum.user.service;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDTO;
import ru.practicum.user.dto.UsersListRequest;

import java.util.List;

public interface UserAdminService {
    UserDTO addUser(NewUserRequest newUser);

    void deleteUserById(Integer id);

    UserDTO getUserById(Integer id);

    List<UserDTO> getUsers(UsersListRequest request);
}
