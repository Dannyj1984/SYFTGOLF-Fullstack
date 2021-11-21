package com.syftgolf.syftgolf.user.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserPasswordUpdateVM {

    @NotNull
    @Size(min = 8, max=255 , message = "{javax.validation.constraints.Size.message}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="{syftgolf.constraints.password.Pattern.message}")
    private String password;
}
