package com.syftgolf.syftgolf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int h1Index, h2Index, h3Index, h4Index, h5Index, h6Index, h7Index, h8Index, h9Index, h10Index, h11Index, h12Index, h13Index, h14Index, h15Index, h16Index, h17Index, h18Index;

    private int h1Score = 0;
    private int h2Score = 0;
    private int h3Score = 0;
    private int h4Score = 0;
    private int h5Score = 0;
    private int h6Score = 0;
    private int h7Score = 0;
    private int h8Score = 0;
    private int h9Score = 0;
    private int h10Score = 0;
    private int h11Score = 0;
    private int h12Score = 0;
    private int h13Score = 0;
    private int h14Score = 0;
    private int h15Score = 0;
    private int h16Score = 0;
    private int h17Score = 0;
    private int h18Score = 0;

    private int h1NettScore = 0;
    private int h2NettScore = 0;
    private int h3NettScore = 0;
    private int h4NettScore = 0;
    private int h5NettScore = 0;
    private int h6NettScore = 0;
    private int h7NettScore = 0;
    private int h8NettScore = 0;
    private int h9NettScore = 0;
    private int h10NettScore = 0;
    private int h11NettScore = 0;
    private int h12NettScore = 0;
    private int h13NettScore = 0;
    private int h14NettScore = 0;
    private int h15NettScore = 0;
    private int h16NettScore = 0;
    private int h17NettScore = 0;
    private int h18NettScore = 0;

    private int h1Points = 0;
    private int h2Points = 0;
    private int h3Points = 0;
    private int h4Points = 0;
    private int h5Points = 0;
    private int h6Points = 0;
    private int h7Points = 0;
    private int h8Points = 0;
    private int h9Points = 0;
    private int h10Points = 0;
    private int h11Points = 0;
    private int h12Points = 0;
    private int h13Points = 0;
    private int h14Points = 0;
    private int h15Points = 0;
    private int h16Points = 0;
    private int h17Points = 0;
    private int h18Points = 0;



    private int totalMedalScore = 0;
    private int totalStablefordScore = 0;
    private int totalNettScore = 0;

    /**
     * Link a list of entrants to a scorecard
     */
    @JsonIgnore
    @OneToOne(mappedBy = "scoreCard")
    private Entrants entrants;


}
