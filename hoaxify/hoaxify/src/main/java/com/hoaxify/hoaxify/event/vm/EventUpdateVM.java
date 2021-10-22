package com.hoaxify.hoaxify.event.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
public class EventUpdateVM {

    @NotNull
    @Size(min=4, max=255)
    private String eventname;

    @NotNull
    private long courseid;

    @NotNull
    private LocalDate date;

    @NotNull
    private int maxentrants;

    private double cost;

    private String eventtype;

    private Boolean qualifier;

    private String info;

    private String winner;
}
