package com.syftgolf.syftgolf.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Hole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private int holeNumber;
    @NotNull
    private int strokeIndex;
    @NotNull
    private int yards;
    @NotNull
    private int par;
    @Column(
            name = "holeIdentifier",
            unique = true
    )
    private String holeIdentifier;


    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Hole(int holeNumber, int strokeIndex, int yards, int par) {
        this.holeNumber = holeNumber;
        this.strokeIndex = strokeIndex;
        this.yards = yards;
        this.par = par;
    }
}
