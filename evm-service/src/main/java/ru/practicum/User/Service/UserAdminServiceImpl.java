package ru.practicum.User.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.Exception.NotFoundException;
import ru.practicum.User.DTO.NewUserRequest;
import ru.practicum.User.DTO.UserDTO;
import ru.practicum.User.DTO.UsersListRequest;
import ru.practicum.User.Mapper.UserMapper;
import ru.practicum.User.Model.User;
import ru.practicum.User.Repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO addUser(NewUserRequest newUser) {
        return userMapper.toUserDTO(userRepository.save(userMapper.toUser(newUser)));
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));

        userRepository.delete(user);
    }

    @Override
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));

        return userMapper.toUserDTO(user);
    }

    @Override
    public List<UserDTO> getUsers(UsersListRequest request) {
        int page = request.getFrom() / request.getSize();
        Pageable pageable = PageRequest.of(page, request.getSize(), Sort.by("id").ascending());

        List<User> users = request.getIds() == null || request.getIds().isEmpty()
                ? userRepository.findAll(pageable).getContent()
                : userRepository.findByIdIn(request.getIds(), pageable).getContent();

        return users.stream()
                .map(userMapper::toUserDTO)
                .toList();
    }
}
