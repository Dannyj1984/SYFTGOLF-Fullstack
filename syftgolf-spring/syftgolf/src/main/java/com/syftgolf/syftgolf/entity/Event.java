package com.syftgolf.syftgolf.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate date;

    private int maxEntrants;

    private double cost;

    private int currentEntrants = 0;

    private String winner;

    private Boolean qualifier;

    @NotNull
    @Column(name = "ninetyfivepercent")
    private Boolean ninetyFivePercent;

    @NotNull
    @Column(name = "major")
    private Boolean major;

    @NotNull
    private String type;

    private String info;

    private String status = "open";

    //For testing

    public Event(String name, LocalDate date, int maxEntrants, String type){
        this.name = name;
        this.date = date;
        this.maxEntrants = maxEntrants;
        this.type = type;
    }

    public Event(String name, LocalDate date, int maxEntrants, String type, String info, double cost, boolean qualifier, boolean ninetyFivePercent, Course course){
        this.name = name;
        this.date = date;
        this.qualifier = qualifier;
        this.info = info;
        this.cost = cost;
        this.maxEntrants = maxEntrants;
        this.type = type;
        this.ninetyFivePercent = ninetyFivePercent;
        this.course = course;
    }

    /**
     * Link an event to a course
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    /**
     * Link an event to a tournament tournament
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    /**
     * Link an event to a society
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;


    /**
     * Link a list of entrants to an event
     */
    @JsonIgnore
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<Entrants> entrants;

    /**
     * Link a list of teesheet to an event
     */
    @JsonIgnore
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<TeeSheet> teeSheets;




    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", course=" + course +
                '}';
    }
}
