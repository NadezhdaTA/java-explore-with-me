package ru.practicum.Event.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Event.Model.State;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {

    @NotNull
    @NotEmpty
    private ArrayList<Integer> requestIds;

    @NotNull
    private State status;

}
