package com.syftgolf.syftgolf.entity.vm;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ScoreCardVM {

    @NotNull
    private int h1Score = 0;
    @NotNull
    private int h2Score = 0;
    @NotNull
    private int h3Score = 0;
    @NotNull
    private int h4Score = 0;
    @NotNull
    private int h5Score = 0;
    @NotNull
    private int h6Score = 0;
    @NotNull
    private int h7Score = 0;
    @NotNull
    private int h8Score = 0;
    @NotNull
    private int h9Score = 0;
    @NotNull
    private int h10Score = 0;
    @NotNull
    private int h11Score = 0;
    @NotNull
    private int h12Score = 0;
    @NotNull
    private int h13Score = 0;
    @NotNull
    private int h14Score = 0;
    @NotNull
    private int h15Score = 0;
    @NotNull
    private int h16Score = 0;
    @NotNull
    private int h17Score = 0;
    @NotNull
    private int h18Score = 0;
}
