package com.syftgolf.syftgolf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Matchplay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int year;

    private String winner;

    public Matchplay(Matchplay matchplay) {
        this.setName(matchplay.getName());
        this.setYear(matchplay.getYear());
    }

    /**
     * Link a list of entrants to an matchplay
     */
    @JsonIgnore
    @OneToMany(mappedBy = "matchplay", fetch = FetchType.LAZY)
    private List<MatchPlayer> matchPlayer;

    @JsonIgnore
    @OneToMany(mappedBy = "matchplay")
    private List<RoundRobin> roundrobin;

    /**
     * Link an event to a society
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;
}
