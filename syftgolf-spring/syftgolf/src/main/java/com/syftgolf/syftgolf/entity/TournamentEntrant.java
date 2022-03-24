package com.syftgolf.syftgolf.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tournament_entrant")
@IdClass(TournamentMemberId.class)
@Data
@EqualsAndHashCode(exclude = { "member"})
public class TournamentEntrant {

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;

    @Column(name = "totalScore")
    private double totalScore;

    public TournamentEntrant() {
    }

    public TournamentEntrant(Member member, Tournament tournament, double totalScore) {
        this.member = member;
        this.tournament = tournament;
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "Entrants{" +
                "member=" + member.toString() +
                ", event=" + tournament.toString() +
                ", totalScore=" + totalScore +
                '}';
    }
}
