package ru.practicum.Event.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Event.Model.State;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {
    private ArrayList<Integer> requestIds;
    private State status;

}
