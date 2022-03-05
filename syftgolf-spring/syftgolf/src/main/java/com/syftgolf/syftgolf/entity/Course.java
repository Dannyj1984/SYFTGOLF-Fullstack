package com.syftgolf.syftgolf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private int par;

    @NotNull
    private double slopeRating;

    @NotNull
    private double courseRating;

    public Course() {}

    public Course(String name, int par, double slopeRating, double courseRating) {
        this.name = name;
        this.par = par;
        this.slopeRating = slopeRating;
        this.courseRating = courseRating;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", par=" + par +
                '}';
    }

    /**
     * Link a list of events to a course
     */
    @JsonIgnore
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<Event> events;

    /**
     * Link a course to a society
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;

    /**
     * Link a list of Holes to an event
     */
    @JsonIgnore
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Hole> holes;
}
