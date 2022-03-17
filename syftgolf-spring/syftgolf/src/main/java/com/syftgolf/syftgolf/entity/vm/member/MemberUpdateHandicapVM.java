package com.syftgolf.syftgolf.entity.vm.member;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class MemberUpdateHandicapVM {

    @NotNull(message = "Please enter a handicap for this member")
    private double handicap;

    @NotNull(message = "Please enter a handicap reduction for this member")
    private int socHcpRed;
}
