package com.syftgolf.syftgolf.user.vm;

import com.syftgolf.syftgolf.user.UniqueEmail;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.syftgolf.syftgolf.shared.ProfileImage;

@Data
public class UserUpdateVM {

    @NotNull
    @Size(min=4, max=255)
    private String username;

    @NotNull
    private double handicap;

    @NotNull(message = "{syftgolf.constraints.email.NotNull.message}")
    @Size(min = 6, max=255, message = "{javax.validation.constraints.Size.message}")
//    @Email(message = "{syftgolf.constraints.email.invalid.message}")
//    @UniqueEmail
    private String email;

    @NotNull
    @Size(min = 11, max=11, message = "Please enter a valid mobile number which should be 11 digits long")
    private String mobile;


    private String homeclub;

    @ProfileImage
    private String image;

}
