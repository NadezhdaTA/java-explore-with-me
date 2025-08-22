package ru.practicum.DTO.CompilationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompilationRequest {
    private ArrayList<Integer> events;
    private Boolean pinned;

    @Length(min = 1, max = 50)
    private String title;
}
