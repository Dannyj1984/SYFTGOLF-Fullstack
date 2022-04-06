package com.syftgolf.syftgolf.entity.vm.event;

import com.syftgolf.syftgolf.entity.Entrants;
import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.TeeSheet;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.events.EntityReference;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class EventVM {

    private long id;
    private String name;
    private long course_id;
    private LocalDate date;
    private int maxEntrants;
    private int currentEntrants;
    private double cost;
    private String type;
    private String info;
    private Boolean qualifier;
    private Boolean ninetyFivePercent;
    private Boolean major;
    private String winner;
    private String status;
    private List<TeeSheet> teesheet;
    private List<Entrants> entrants;

    public EventVM(Event event) {
        this.setId(event.getId());
        this.setName(event.getName());
        this.setType(event.getType());
        this.setDate(event.getDate());
        this.setCost(event.getCost());
        this.setInfo(event.getInfo());
        this.setQualifier(event.getQualifier());
        this.setMaxEntrants(event.getMaxEntrants());
        this.setCurrentEntrants(event.getCurrentEntrants());
        this.setWinner(event.getWinner());
        this.setStatus(event.getStatus());
        this.setTeesheet(event.getTeeSheets());
        this.setEntrants(event.getEntrants());
        this.setNinetyFivePercent(event.getNinetyFivePercent());
        this.setMajor(event.getMajor());
    }
}
