package ru.practicum.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.User.Model.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "annotation")
    private String annotation;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @Column(name = "description")
    private String description;

    @Column(name = "eventDate")
    private LocalDateTime eventDate;

    @OneToOne(fetch = FetchType.EAGER)
    private User initiator;

    @OneToOne(fetch = FetchType.EAGER)
    private Location location;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participantLimit")
    private Integer participantLimit;

    @Column(name = "publishedOn")
    private LocalDateTime publishedOn;

    @Column(name = "requestModeration")
    private Boolean requestModeration;

    @OneToOne(fetch = FetchType.EAGER)
    private State state;


    private String title;

    private Integer views;
}
