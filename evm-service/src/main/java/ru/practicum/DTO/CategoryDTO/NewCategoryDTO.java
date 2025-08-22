package ru.practicum.DTO.CategoryDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDTO {
    @NotBlank
    @Length(max = 50)
    private String name;
}
