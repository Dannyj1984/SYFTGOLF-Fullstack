package com.syftgolf.syftgolf.user.vm;

import com.syftgolf.syftgolf.user.UniqueEmail;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.syftgolf.syftgolf.shared.ProfileImage;
import org.hibernate.validator.constraints.Range;

@Data
public class UserUpdateVM {

    @NotNull(message = "Please enter a username for this member")
    @Size(min=4, max=255)
    private String username;

    @NotNull(message = "Please enter a handicap for this member")
    @Range(min = -10, max = 54)
    private double handicap;

    @NotNull(message = "{syftgolf.constraints.email.NotNull.message}")
    @Size(min = 6, max=255, message = "{javax.validation.constraints.Size.message}")
//    @Email(message = "{syftgolf.constraints.email.invalid.message}")
//    @UniqueEmail
    private String email;

    @NotNull
    @Size(min = 11, max=11, message = "Please enter a valid mobile number which should be 11 digits long")
    private String mobile;

    private String cdh;


    private String homeclub;

    @ProfileImage
    private String image;

}
