package com.hoaxify.hoaxify.event;



import com.hoaxify.hoaxify.course.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
//Entity maps object to database
@Entity
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue
    @Column(name = "eventid")
    private long eventid;

    @NotNull(message = "{hoaxify.constraints.eventname.NotNull.message}")
    @Size(min = 4, max=255)
    @UniqueEventName
    @Column(
            name = "eventname",
            columnDefinition = "TEXT")
    private String eventname;

    @NotNull
    @Column(
            name = "date",
            columnDefinition = "TEXT"
    )
    private String date;

    @NotNull
    @Column(
            name = "maxentrants",
            columnDefinition = "INTEGER"
    )
    private int maxentrants;

    @NotNull
    @Column(
            name = "cost",
            columnDefinition = "MONEY"
    )

    private BigDecimal cost;

    @NotNull
    @Column(
            name = "eventtype",
            columnDefinition = "TEXT"
    )

    private String eventtype;

    private String image;

    @Column(
            name = "qualifier"
    )
    private Boolean qualifier;

    private int courseid;

    private String winner;

    private String info;




}
