package com.syftgolf.syftgolf.user.vm;

import com.syftgolf.syftgolf.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWinVM {

        private long id;

        private int wins;

        public UserWinVM(User user) {
            this.setId(user.getId());
            this.setWins(user.getWins());
        }
}

