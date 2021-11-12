package com.syftgolf.syftgolf.user.vm;

import com.syftgolf.syftgolf.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserHandicapVM {

    private long id;

    private double handicap;

    private int sochcpred;

    public UserHandicapVM(User user) {
        this.setId(user.getId());
        this.setHandicap(user.getHandicap());
        this.setSochcpred(user.getSochcpred());
    }
}
