package ru.practicum.User.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.User.Model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User save(User newUser);

    void deleteUserById(Integer id);

    Optional<User> findUserById(Integer id);

    Page<User> findAll(Pageable pageable);

    Page<User> findAllByIdIn(Collection<Integer> ids, Pageable pageable);
}
