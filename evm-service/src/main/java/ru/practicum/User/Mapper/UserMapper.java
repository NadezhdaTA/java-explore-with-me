package ru.practicum.User.Mapper;

import org.mapstruct.Mapper;
import ru.practicum.User.DTO.NewUserRequest;
import ru.practicum.User.DTO.UserDTO;
import ru.practicum.User.DTO.UserShortDTO;
import ru.practicum.User.Model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(NewUserRequest user);

    UserDTO toUserDTO(User user);

    UserShortDTO toUserShortDTO(User user);
}
