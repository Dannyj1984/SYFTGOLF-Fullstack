package com.hoaxify.hoaxify.course;

import java.io.Serializable;
//Create composite PK for the hole table on hole and yards
public class HoleId implements Serializable {

    private int hole;

    private int yards;

    private int stroke;
}