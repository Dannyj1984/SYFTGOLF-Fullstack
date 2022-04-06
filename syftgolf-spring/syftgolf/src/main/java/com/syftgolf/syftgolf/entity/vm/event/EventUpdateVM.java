package com.syftgolf.syftgolf.entity.vm.event;

import com.syftgolf.syftgolf.entity.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EventUpdateVM {

    private String name;
    private LocalDate date;
    private Boolean qualifier;
    private Boolean ninetyFivePercent;
    private Boolean major;
    private String type;
    private int maxEntrants;
    private String info;
    private String status;
    private double cost;
    private String winner;
}
