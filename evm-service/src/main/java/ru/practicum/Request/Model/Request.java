package ru.practicum.Request.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Event.Model.Event;
import ru.practicum.Event.Model.State;
import ru.practicum.User.Model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created")
    private final LocalDateTime created = LocalDateTime.now();

    @OneToOne
    private Event event;

    @OneToOne(fetch = FetchType.EAGER)
    private User requester;

    @Column(name = "state_id")
    @Enumerated(EnumType.ORDINAL)
    private State status;
}
