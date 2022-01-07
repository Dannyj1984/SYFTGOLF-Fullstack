package com.syftgolf.syftgolf.course.hole;

import java.io.Serializable;
import java.util.Objects;

//Create composite PK for the hole table on hole and yards
public class HoleId implements Serializable {

    private int hole;

    private int yards;

    private int stroke;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoleId holeId = (HoleId) o;
        return hole == holeId.hole && yards == holeId.yards && stroke == holeId.stroke;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hole, yards, stroke);
    }
}
