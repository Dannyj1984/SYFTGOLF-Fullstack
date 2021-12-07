package com.syftgolf.syftgolf.event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syftgolf.syftgolf.course.Course;
import com.syftgolf.syftgolf.course.hole.Hole;
import com.syftgolf.syftgolf.event.newteesheet.NewTeeSheet;
import com.syftgolf.syftgolf.event.teesheet.TeeSheet;
import com.syftgolf.syftgolf.society.Society;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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

    @NotNull(message = "{syftgolf.constraints.eventname.NotNull.message}")
    @Size(min = 4, max=255)
    @UniqueEventName
    @Column(
            name = "eventname",
            columnDefinition = "TEXT")
    private String eventname;

    @NotNull(message = "{syftgolf.constraints.eventdate.NotNull.message}")
    @Column(
            name = "date",
            columnDefinition = "DATE"
    )
    private Date date;

    @NotNull(message = "{syftgolf.constraints.maxentrants.NotNull.message}")
    @Range(min = 1, max = 100, message = "Please enter a value for the maximum number of entrants between 1 and 100")
    @Column(
            name = "maxentrants",
            columnDefinition = "INTEGER"
    )
    private int maxentrants;

    @NotNull
    @Column(
            name = "currententrants",
            columnDefinition = "INTEGER"
    )
    private int currententrants;

    @NotNull
    @Column(
            name = "cost",
            columnDefinition = "NUMERIC"
    )
    @Range(min = 1, max = 250, message = "Please enter the cost of this event, between 1 and 250")
    private double cost;

    @NotNull(message = "{syftgolf.constraints.eventtype.NotNull.message}")
    @Column(
            name = "eventtype",
            columnDefinition = "TEXT"
    )
    @Size(min = 1, max = 30, message = "Please enter an event type")
    private String eventtype;

    @NotNull(message = "{syftgolf.constraints.qualifier.NotNull.message}")
    @Column(
            name = "qualifier"
    )
    private Boolean qualifier;

    private String winner;

    private String info;


    //relationship with event and course
    @NotNull(message = "Please choose a course")
    @ManyToOne
    @JoinColumn(name = "course_id")
    @ToString.Exclude
    private Course course;

    //Relationship for event and teesheet
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teesheet_id", referencedColumnName = "teesheetid")
    private TeeSheet teeSheet;

    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;

    //Relationship for newteesheet
    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<NewTeeSheet> newteesheets;


}


