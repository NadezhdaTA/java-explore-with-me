package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDTO;
import ru.practicum.user.dto.UsersListRequest;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO addUser(NewUserRequest newUser) {
        UserDTO userDTO = userMapper.toUserDTO(userRepository.save(userMapper.toUser(newUser)));
        log.info("User is added: {}", userDTO);
        return userDTO;
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));

        userRepository.delete(user);
        log.info("User with id {} deleted", id);
    }

    @Override
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));

        UserDTO userDTO = userMapper.toUserDTO(user);
        log.info("User with id = {} is found: {}", id, userDTO);
        return userDTO;
    }

    @Override
    public List<UserDTO> getUsers(UsersListRequest request) {
        int page = request.from() / request.size();
        Pageable pageable = PageRequest.of(page, request.size(), Sort.by("id").ascending());

        List<User> users = request.ids() == null || request.ids().isEmpty()
                ? userRepository.findAll(pageable).getContent()
                : userRepository.findByIdIn(request.ids(), pageable).getContent();

        log.info("Found {} users are sent", users.size());
        return users.stream()
                .map(userMapper::toUserDTO)
                .toList();
    }
}
