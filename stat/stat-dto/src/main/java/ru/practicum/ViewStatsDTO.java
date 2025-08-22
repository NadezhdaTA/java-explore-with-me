package ru.practicum;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewStatsDTO {

    @NotBlank
    private String app;

    @NotBlank
    private String uri;
    private Integer hits;
}
