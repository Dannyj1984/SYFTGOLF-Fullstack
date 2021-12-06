package com.syftgolf.syftgolf.society;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syftgolf.syftgolf.course.Course;
import com.syftgolf.syftgolf.event.Event;
import com.syftgolf.syftgolf.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "society")
public class Society {

    @Id
    @GeneratedValue
    @Column(name = "society_id")
    private long id;

    @NotNull
    @Column(name = "society_name")
    private String name;

    //Relationship for members
    @JsonIgnore
    @OneToMany(mappedBy = "society")
    private List<User> users;

    //Relationship for courses
    @JsonIgnore
    @OneToMany(mappedBy = "society")
    private List<Course> courses;

    //Relationship for events
    @JsonIgnore
    @OneToMany(mappedBy = "society")
    private List<Event> events;

    private int members;

    public Society(String name) {
        this.name = name;
    }
}
