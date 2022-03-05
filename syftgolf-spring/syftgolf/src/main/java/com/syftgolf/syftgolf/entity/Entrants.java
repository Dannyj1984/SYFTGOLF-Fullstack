package com.syftgolf.syftgolf.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "entrants")
@IdClass(EventMemberId.class)
@Data
@EqualsAndHashCode(exclude = {"scoreCard", "member"})
public class Entrants {

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scorecard_id", referencedColumnName = "id")
    private ScoreCard scoreCard;

    @NotNull
    @Column(name = "score")
    private double score = 0;

    @NotNull
    @Column(name = "current_hole")
    private Integer currentHole = 0;

    public Entrants() {}

    public Entrants(Member member, Event event, double score, int currentHole) {
        this.member = member;
        this.event = event;
        this.score = score;
        this.currentHole = currentHole;
    }

    @ManyToMany
    private List<TeeSheet> teeSheets;

    @Override
    public String toString() {
        return "Entrants{" +
                "member=" + member.getFirstName() +
                ", event=" + event.getName() +
                ", score=" + score +
                '}';
    }
}
