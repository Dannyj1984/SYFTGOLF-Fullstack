package com.syftgolf.syftgolf.course.hole;

import lombok.Data;

import java.io.Serializable;
//Create composite PK for the hole table on hole and yards
@Data
public class HoleId implements Serializable {

    private int hole;

    private int yards;

    private int stroke;
}
