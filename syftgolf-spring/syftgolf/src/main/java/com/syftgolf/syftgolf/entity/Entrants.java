package com.syftgolf.syftgolf.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    public static Comparator<Entrants> entrantScoreMedal = new Comparator<Entrants>() {

        public int compare(Entrants s1, Entrants s2) {

            int score1 = (int) Math.round(s1.getScore());
            int score2 = (int) Math.round(s2.getScore());

            /*For ascending order*/
            return score1-score2;
        }};

    /*Comparator for sorting score descending for stableford*/
    public static Comparator<Entrants> entrantScoreStableford = new Comparator<Entrants>() {

        public int compare(Entrants s1, Entrants s2) {

            int score1 = (int) Math.round(s1.getScore());
            int score2 = (int) Math.round(s2.getScore());

            /*For ascending order*/
            return score2-score1;
        }};

    @Override
    public String toString() {
        return "Entrants{" +
                "member=" + member.getFirstName() +
                ", event=" + event.getName() +
                ", score=" + score +
                '}';
    }
}
