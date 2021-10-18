package com.hoaxify.hoaxify.event.vm;

import com.hoaxify.hoaxify.event.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class EventVM {

    private Long id;

    private String eventname;

    private int courseid;

    private String date;

    private int maxentrants;

    private BigDecimal cost;

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
    }
}
