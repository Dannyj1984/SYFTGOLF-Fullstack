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

    private String society_hcp_reduction;

    private String societyHandicap;


    public UserVM(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setFirstname(user.getFirstname());
        this.setSurname(user.getSurname());
        this.setHandicap(user.getHandicap());
        this.setMobile(user.getMobile());
        this.setCdh(user.getCdh());
        this.setSociety_hcp_reduction(user.getSociety_hcp_reduction());
        this.setSocietyHandicap(user.getSocietyHandicap());
    }

}
