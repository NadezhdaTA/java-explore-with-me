package ru.practicum.User.Service;

import ru.practicum.User.DTO.NewUserRequest;
import ru.practicum.User.DTO.UserDTO;
import ru.practicum.User.DTO.UsersListRequest;

import java.util.List;

public interface UserAdminService {
    UserDTO addUser(NewUserRequest newUser);

    void deleteUserById(Integer id);

    UserDTO getUserById(Integer id);

    List<UserDTO> getUsers(UsersListRequest request);
}
