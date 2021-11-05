package com.hoaxify.hoaxify.event;
import com.hoaxify.hoaxify.course.Course;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
//Entity maps object to database
@Entity
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "eventid")
    private long eventid;

    @NotNull(message = "{hoaxify.constraints.eventname.NotNull.message}")
    @Size(min = 4, max=255)
    @UniqueEventName
    @Column(
            name = "eventname",
            columnDefinition = "TEXT")
    private String eventname;

    @NotNull(message = "{hoaxify.constraints.eventdate.NotNull.message}")
    @Column(
            name = "date",
            columnDefinition = "DATE"
    )
    private LocalDate date;

    @NotNull(message = "{hoaxify.constraints.eventname.NotNull.message}")
    @Column(
            name = "maxentrants",
            columnDefinition = "INTEGER"
    )
    private int maxentrants;

    @NotNull
    @Column(
            name = "cost",
            columnDefinition = "NUMERIC"
    )

    private double cost;

    @NotNull(message = "{hoaxify.constraints.eventtype.NotNull.message}")
    @Column(
            name = "eventtype",
            columnDefinition = "TEXT"
    )
    private String eventtype;

    @NotNull(message = "{hoaxify.constraints.qualifier.NotNull.message}")
    @Column(
            name = "qualifier"
    )
    private Boolean qualifier;

    private String winner;

    private String info;

    //relationship with event and course
    @ManyToOne
    @JoinColumn(name = "course_id")
    @ToString.Exclude
    private Course course;

    @ToString.Exclude
    @OneToMany(mappedBy = "event")
    Set<Entrants> entrants;


}


