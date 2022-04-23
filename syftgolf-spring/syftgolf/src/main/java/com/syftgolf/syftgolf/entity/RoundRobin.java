package com.syftgolf.syftgolf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roundrobin")
@Data
public class RoundRobin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private long id;


    @ManyToMany
    private List<MatchPlayer> players;


    private int p1Score = 0;

    private int p2Score = 0;

    private int round;

    @ManyToOne
    @JoinColumn(name = "matchplay_id", referencedColumnName = "id")
    private Matchplay matchplay;



    public RoundRobin() {}

    public RoundRobin(List<MatchPlayer> players, int round) {
        this.setPlayers(players);
        this.setRound(round);
    }

    @Override
    public String toString() {
        return "Players{" +
                "players='" + players + '\'' +
                "round='" + round + '\'' +
                '}';
    }

}
