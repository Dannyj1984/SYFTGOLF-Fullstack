package com.syftgolf.syftgolf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syftgolf.syftgolf.entity.vm.member.MemberVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "matchPlayer")
@IdClass(MatchPlayerId.class)
@Data
@EqualsAndHashCode(exclude = {"matchplay", "member"})
public class MatchPlayer {

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "matchplay_id", referencedColumnName = "id")
    private Matchplay matchplay;

    @Column(name = "points")
    int points = 0;

    @Column(name = "score")
    int score = 0;

    @Column(name = "grouping")
    int grouping = 0;

    @JsonIgnore
    @ManyToMany(mappedBy = "players")
    private List<RoundRobin> rounds;


    public MatchPlayer() {}

    public MatchPlayer(Member member, Matchplay matchplay) {
        this.member = member;
        this.matchplay = matchplay;
    }

    @Override
    public String toString() {
        return "Matchplayer{" +
                "member=" + member.getFirstName() + member.getSurname() +
                "group" + getGrouping() +
                ", matchplay=" + matchplay.getName() +
                '}';
    }
}
