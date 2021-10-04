package com.hoaxify.hoaxify.user.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.hoaxify.hoaxify.shared.ProfileImage;

@Data
public class UserUpdateVM {

    @NotNull
    @Size(min=4, max=255)
    private String username;

    @ProfileImage
    private String image;

}
