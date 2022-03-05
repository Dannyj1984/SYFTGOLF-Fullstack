package com.syftgolf.syftgolf.entity.vm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TournamentVM {

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private int noOfEvents;

    private String type;
}
