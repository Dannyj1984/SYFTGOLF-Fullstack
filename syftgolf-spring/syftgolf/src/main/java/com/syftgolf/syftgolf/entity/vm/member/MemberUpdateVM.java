package com.syftgolf.syftgolf.entity.vm.member;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MemberUpdateVM {

    @NotNull(message = "Please enter a username for this member")
    @Size(min=4, max=255)
    private String username;

    @NotNull(message = "Please enter a handicap for this member")
    @Range(min = -10, max = 54)
    private double handicap;

    @NotNull
    @Size(min = 6, max=255)
    private String email;

    @NotNull
    @Size(min = 11, max=11, message = "Please enter a valid mobile number which should be 11 digits long")
    private String mobile;

    private String cdh;

    private String image;

    private String homeClub;

}
