package ru.practicum.Compilation.CompilationDTO;

import lombok.Data;

@Data
public class CompilationsListRequestParams {
    private Boolean pinned;
    private Integer from = 0;
    private Integer size = 10;
}
