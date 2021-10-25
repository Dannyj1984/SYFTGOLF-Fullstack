package com.hoaxify.hoaxify.course;

import com.hoaxify.hoaxify.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
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
    @GeneratedValue
    @Column(name = "courseid")
    private long courseid;

    @NotNull(message = "{hoaxify.constraints.coursename.NotNull.message}")
    @Size(min = 4, max=255)
    @UniqueCourseName
    @Column(
            name = "coursename",
            columnDefinition = "TEXT")
    private String courseName;

    @NotNull
    @Size(min = 4, max = 9)
    @Column(
            name = "postcode"
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private List<Event> events = new ArrayList<>();


}

