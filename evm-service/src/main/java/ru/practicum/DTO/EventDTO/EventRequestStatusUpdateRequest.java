package ru.practicum.DTO.EventDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Model.State;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {
    private ArrayList<Integer> requestIds;
    private State status;

}
