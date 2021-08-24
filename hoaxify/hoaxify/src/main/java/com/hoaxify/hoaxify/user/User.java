package com.hoaxify.hoaxify.user;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
//Entity maps object to database
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "{hoaxify.constraints.username.NotNull.message}")
    @Size(min = 4, max=255)
    @UniqueUsername
    private String username;

    @NotNull(message = "{hoaxify.constraints.email.NotNull.message}")
    @Size(min = 6, max=255, message = "{javax.validation.constraints.Size.message}")
    @Email(message = "{hoaxify.constraints.email.invalid.message}")
    @UniqueEmail
    private String email;

    @NotNull(message = "{hoaxify.constraints.name.NotNull.message}")
    @Size(min = 4, max=255)
    private String firstname;

    @NotNull(message = "{hoaxify.constraints.name.NotNull.message}")
    @Size(min = 4, max=255, message = "{javax.validation.constraints.Size.message}")
    private String surname;

    @NotNull(message = "Please enter a handicap for this member")
    @Size(min = 1, max=4, message = "Please enter a handicap for this member")
    private String handicap;

    @NotNull
    @Size(min = 11, max=11, message = "Please enter a valid mobile number which should be 11 digits long")
    private String mobile;

    @NotNull
    @Size(min = 10, max=10, message = "Please enter a valid CDH number which should be 10 digits long")
    private String cdh;

    @NotNull
    private String society_hcp_reduction = "0.0";

    @NotNull
    private String societyHandicap = "0";

    @NotNull
    @Size(min = 8, max=255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="{hoaxify.constraints.password.Pattern.message}")
    private String password;


}
