package com.syftgolf.syftgolf.event.vm;

import com.syftgolf.syftgolf.event.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class EventVM {

    private Long id;

    private String eventname;

    private long course_id;

    private LocalDate date;

    private int maxentrants;

    private int currententrants;

    private double cost;

    private String eventtype;

    private Boolean qualifier;

    private String info;

    private String winner;

    public EventVM(Event event) {
        this.setId(event.getEventid());
        this.setEventname(event.getEventname());
        this.setEventtype(event.getEventtype());
        this.setDate(event.getDate());
        this.setCost(event.getCost());
        this.setInfo(event.getInfo());
        this.setQualifier(event.getQualifier());
        this.setMaxentrants(event.getMaxentrants());
        this.setWinner(event.getWinner());
//        this.setCourse_id(event.getCourse_id());
    }
}
