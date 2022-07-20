package com.syftgolf.syftgolf.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Comparator;
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

    @Column(name = "coursehcp")
    private int coursehcp = 0;

    @NotNull
    @Column(name = "NR")
    private boolean NR = false;

    public Entrants() {}

    public Entrants(Member member, Event event, double score, int currentHole) {
        this.member = member;
        this.event = event;
        this.score = score;
        this.currentHole = currentHole;
    }

    @ManyToMany
    private List<TeeSheet> teeSheets;

    /*Comparator for sorting score ascending for medal*/
    public static Comparator<Entrants> entrantScoreMedal = (s1, s2) -> {

        double score1 =  s1.getScore();
        double score2 =  s2.getScore();

        /*For ascending order*/
        return (int) (score1-score2);
    };

    /*Comparator for sorting score descending for stableford*/
    public static Comparator<Entrants> entrantScoreStableford = (s1, s2) -> {

        double score1 =  s1.getScore();
        double score2 =  s2.getScore();

        /*For descending order*/
        return (int) (score2-score1);
    };

    @Override
    public String toString() {
        return "Entrants{" +
                "member=" + member.getFirstName() + member.getSurname() +
                ", event=" + event.getName() +
                ", score=" + score +
                '}';
    }
}
