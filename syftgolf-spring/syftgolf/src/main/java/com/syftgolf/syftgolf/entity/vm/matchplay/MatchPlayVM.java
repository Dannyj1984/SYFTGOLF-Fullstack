package com.syftgolf.syftgolf.entity.vm.matchplay;

import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.MatchPlayer;
import com.syftgolf.syftgolf.entity.Matchplay;
import com.syftgolf.syftgolf.entity.RoundRobin;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MatchPlayVM {

    private long id;
    private String name;
    private int year;
    private String winner;
    private List<MatchPlayer> players;
    private List<RoundRobin> rounds;

    public MatchPlayVM(Matchplay matchplay) {
        this.setId(matchplay.getId());
        this.setName(matchplay.getName());
        this.setYear(matchplay.getYear());
        this.setWinner(matchplay.getWinner());
        this.setPlayers(matchplay.getMatchPlayer());
        this.setRounds(matchplay.getRoundrobin());
    }
}
