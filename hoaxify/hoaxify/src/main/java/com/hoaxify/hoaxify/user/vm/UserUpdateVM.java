package com.hoaxify.hoaxify.user.vm;

import com.hoaxify.hoaxify.user.UniqueEmail;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.hoaxify.hoaxify.shared.ProfileImage;

@Data
public class UserUpdateVM {

    @NotNull
    @Size(min=4, max=255)
    private String username;

    @NotNull
    private String handicap;

    @NotNull(message = "{hoaxify.constraints.email.NotNull.message}")
    @Size(min = 6, max=255, message = "{javax.validation.constraints.Size.message}")
    @Email(message = "{hoaxify.constraints.email.invalid.message}")
    private String email;

    @NotNull
    @Size(min = 11, max=11, message = "Please enter a valid mobile number which should be 11 digits long")
    private String mobile;


    private String homeClub;

    @ProfileImage
    private String image;

}
