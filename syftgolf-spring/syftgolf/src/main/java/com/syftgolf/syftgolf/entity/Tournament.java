package com.syftgolf.syftgolf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{syftgolf.constraints.tournamentName.NotNull.message}")
    private String name;

    @NotNull(message = "{syftgolf.constraints.date.NotNull.message}")
    private LocalDate startDate;

    @NotNull(message = "{syftgolf.constraints.date.NotNull.message}")
    private LocalDate endDate;

    private int noOfEvents = 0;

    @NotNull(message = "{syftgolf.constraints.type.NotNull.message}")
    private String type;

    private int noOfEntrants = 0;

    private String status = "Open";

    public Tournament(){}

    public Tournament(String name, LocalDate start, LocalDate end, String type) {
        this.name = name;
        this.startDate = start;
        this.endDate = end;
        this.type = type;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "tournament", fetch = FetchType.LAZY)
    private List<Event> events;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;

    /**
     * Link a list of tournamentEntrants to a tournament
     */
    @JsonIgnore
    @OneToMany(mappedBy = "tournament", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TournamentEntrant> tournamentEntrants;


}
