package com.syftgolf.syftgolf.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@IdClass(TournamentMemberId.class)
@Data
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

    public TournamentEntrant(Member member, Tournament multievent, double totalScore) {
        this.member = member;
        this.tournament = multievent;
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
