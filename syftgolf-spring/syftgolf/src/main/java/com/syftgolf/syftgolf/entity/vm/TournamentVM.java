package com.syftgolf.syftgolf.entity.vm;

import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.Tournament;
import com.syftgolf.syftgolf.entity.TournamentEntrant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentVM {

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private int noOfEvents;

    private String type;

    private String status;

    private List<TournamentEntrant> tournamentEntrants;

    private List<Event> events;

    public TournamentVM(Tournament tournament) {
        this.setName(tournament.getName());
        this.setStartDate(tournament.getStartDate());
        this.setEndDate(tournament.getEndDate());
        this.setNoOfEvents(tournament.getNoOfEntrants());
        this.setType(tournament.getType());
        this.setStatus(tournament.getStatus());
        this.setTournamentEntrants(tournament.getTournamentEntrants());
        this.setEvents(tournament.getEvents());
    }
}
