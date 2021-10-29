package com.hoaxify.hoaxify.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hole {

    @Id
    @GeneratedValue
    @Column(name = "holeid")
    private long holeid;

    @NotNull
    private int par;

    @NotNull
    private int number;

    @NotNull
    private int stroke;

    @NotNull
    private int yards;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


}
