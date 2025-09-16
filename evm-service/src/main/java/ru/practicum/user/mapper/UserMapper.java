package ru.practicum.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDTO;
import ru.practicum.user.dto.UserShortDTO;
import ru.practicum.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(NewUserRequest user);

    UserDTO toUserDTO(User user);

    UserShortDTO toUserShortDTO(User user);
}
