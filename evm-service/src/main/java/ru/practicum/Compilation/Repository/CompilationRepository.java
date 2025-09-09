package ru.practicum.Compilation.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.Compilation.Model.Compilation;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {

    Optional<Compilation> findCompilationById(Integer compId);

    Compilation save(Compilation compilation);

    Page<Compilation> findCompilationByPinned(Boolean pinned, Pageable pageable);
}
