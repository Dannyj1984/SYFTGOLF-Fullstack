package com.syftgolf.syftgolf.entity.vm.matchplay;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoundRobinVM {

    private int p1Score;
    private int p2Score;

    public RoundRobinVM(RoundRobinVM rr) {
        this.setP1Score(rr.getP1Score());
        this.setP2Score(rr.getP2Score());
    }
}
