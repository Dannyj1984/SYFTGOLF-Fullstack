package com.syftgolf.syftgolf.user.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserUpdateHandicapVM {

    @NotNull
    private double handicap;

    @NotNull
    private int sochcpred;
}
