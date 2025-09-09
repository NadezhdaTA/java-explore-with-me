package ru.practicum.Compilation.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.Compilation.CompilationDTO.CompilationDTO;
import ru.practicum.Compilation.CompilationDTO.NewCompilationDTO;
import ru.practicum.Compilation.Model.Compilation;

@Mapper(componentModel = "spring")
public interface CompilationMapper {
    CompilationDTO toDto(Compilation compilation);

    Compilation toCompilation(CompilationDTO dto);

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "id", ignore = true)
    Compilation toCompilation(NewCompilationDTO compilation);
}
