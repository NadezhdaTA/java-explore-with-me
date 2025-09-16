package ru.practicum.event.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "annotation", length = 2000)
    private String annotation;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @Column(name = "confirmed_requests")
    private Integer confirmedRequests = 0;

    @Column(name = "created_on")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(name = "description", length = 7000)
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @Embedded
    private Location location;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Column(name = "state_id")
    @Enumerated(EnumType.ORDINAL)
    private State state;

    @Column(name = "views")
    private Integer views = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
