package com.hoaxify.hoaxify.user.vm;

import com.hoaxify.hoaxify.user.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVM {

    private long id;

    private String username;

    private String firstname;

    private String surname;

    private String email;

    private String handicap;

    private String mobile;

    private String cdh;

    private String sochcpred;

    private String socHcp;

    private String homeclub;

    private String authority;

    private int wins;

    private String image;


    public UserVM(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setFirstname(user.getFirstname());
        this.setSurname(user.getSurname());
        this.setHandicap(user.getHandicap());
        this.setMobile(user.getMobile());
        this.setHomeclub((user.getHomeclub()));
        this.setCdh(user.getCdh());
        this.setSochcpred(user.getSochcpred());
        this.setAuthority(user.getAuthority());
        this.setSocHcp(user.getSochcp());
        this.setImage(user.getImage());
        this.setWins(user.getWins());
    }

}
