package com.syftgolf.syftgolf.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(HoleId.class)
public class Hole {

    @NotNull
    private int par;

    @NotNull
    @Id
    private int hole;

    @NotNull
    @Id
    private int stroke;

    @NotNull
    @Id
    private int yards;

    @ManyToOne
    @JoinColumn(name = "course_id")

    private Course course;


}
