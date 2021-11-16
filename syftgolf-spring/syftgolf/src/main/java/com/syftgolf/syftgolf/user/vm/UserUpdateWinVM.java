package com.syftgolf.syftgolf.user.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserUpdateWinVM {

    @NotNull
    private int wins;
}
