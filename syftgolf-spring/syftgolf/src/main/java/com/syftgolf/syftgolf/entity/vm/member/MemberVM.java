package com.syftgolf.syftgolf.entity.vm.member;

import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.entity.Society;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberVM {

    private long id;

    private String username;

    private String firstName;

    private String surname;

    private String email;

    private double handicap;

    private String mobile;

    private String cdh;

    private int socHcpRed;

    private double socHcp;

    private String homeClub;

    private String role;

    private int wins;

    private String image;

    private Society society;

    private int fedExPoints;


    public MemberVM(Member member) {
        this.setId(member.getId());
        this.setUsername(member.getUsername());
        this.setEmail(member.getEmail());
        this.setFirstName(member.getFirstName());
        this.setSurname(member.getSurname());
        this.setHandicap(member.getHandicap());
        this.setMobile(member.getMobile());
        this.setHomeClub((member.getHomeClub()));
        this.setCdh(member.getCdh());
        this.setRole(member.getRole());
        this.setSocHcpRed(member.getSocHcpRed());
        this.setSocHcp(member.getSocHcp());
        this.setImage(member.getImage());
        this.setWins(member.getWins());
        this.setSociety(member.getSociety());
        this.setFedExPoints(member.getFedExPoints());
    }
}
