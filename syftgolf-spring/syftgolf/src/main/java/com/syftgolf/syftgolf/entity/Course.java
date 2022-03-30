package com.syftgolf.syftgolf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @NotNull
    @Pattern(regexp = "^(([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([AZa-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z])))) [0-9][A-Za-z]{2}))$", message = "{syftgolf.constraints.postcode.Pattern.message}")
    private String postcode;

    @NotNull
    private String country;

    public Course() {}

    public Course(String name, int par, double slopeRating, String postcode, String country, double courseRating) {
        this.name = name;
        this.par = par;
        this.slopeRating = slopeRating;
        this.postcode = postcode;
        this.country = country;
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
