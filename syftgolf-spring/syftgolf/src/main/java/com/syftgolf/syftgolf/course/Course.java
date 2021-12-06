package com.syftgolf.syftgolf.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syftgolf.syftgolf.course.hole.Hole;
import com.syftgolf.syftgolf.event.Event;
import com.syftgolf.syftgolf.society.Society;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

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

    @NotNull(message = "{syftgolf.constraints.coursePar.NotNull.message}")
    @Range(min = 60, max = 80, message = "Please enter par with 2 digits")
    @Column(
            name = "par",
            columnDefinition = "INTEGER"
    )
    private int par;

    @NotNull(message = "{syftgolf.constraints.courseRating.NotNull.message}")
    @Range(min = 60, max = 90)
    @Column(
            name = "rating",
            columnDefinition = "DECIMAL(3,1)"
    )
    private double courseRating;

    @NotNull(message = "{syftgolf.constraints.courseSlope.NotNull.message}")
    @Range(min = 80, max = 150)
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

    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;


}

