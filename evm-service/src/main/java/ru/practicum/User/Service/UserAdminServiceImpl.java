package ru.practicum.User.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        Pageable pageable = PageRequest.of(request.getFrom(), request.getSize(), Sort.by("id").ascending());
        Page<User> users = request.getIds() == null
                ? userRepository.findAll(pageable)
                : userRepository.findAllByIdIn(request.getIds(), pageable);

        return users.getContent().stream()
                .map(userMapper::toUserDTO)
                .toList();
    }
}
