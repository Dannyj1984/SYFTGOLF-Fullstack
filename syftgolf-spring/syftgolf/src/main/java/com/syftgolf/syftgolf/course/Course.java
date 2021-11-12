package com.syftgolf.syftgolf.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syftgolf.syftgolf.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
//Entity maps object to database
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "courseid")
    private long courseid;

    @NotNull(message = "{syftgolf.constraints.coursename.NotNull.message}")
    @Size(min = 4, max=255)
    @UniqueCourseName
    @Column(
            name = "coursename",
            columnDefinition = "TEXT")
    private String courseName;

    @NotNull
    @Size(min = 4, max = 9)
    @Column(
            name = "postcode",
            columnDefinition = "TEXT"
    )
    private String postCode;

    @NotNull
    @Column(
            name = "par",
            columnDefinition = "INTEGER"
    )
    private int par;

    @NotNull
    @Column(
            name = "rating",
            columnDefinition = "DECIMAL(3,1)"
    )
    private double courseRating;

    @NotNull
    @Column(
            name = "slope",
            columnDefinition = "INTEGER"
    )
    private int slopeRating;

    private String image;

    //Relationship for events
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Event> events;


    //Relationship for holes
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Hole> holes;


}
