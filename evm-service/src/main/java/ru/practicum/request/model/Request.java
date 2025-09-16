package ru.practicum.request.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
