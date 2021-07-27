package com.hoaxify.hoaxify.user;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    //Add a key so that we can set a suitable message in validationMessages.properties file in resources.
    @NotNull(message = "{hoaxify.constraint.username.NotNull.message}")
    @Size(min = 4, max=255)
    @UniqueUsername
    private String username;

    @NotNull(message = "{hoaxify.constraint.displayName.NotNull.message}")
    @Size(min = 4, max=255)
    private String displayName;

    @NotNull(message = "{hoaxify.constraint.password.NotNull.message}")
    @Size(min = 8, max=255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="{hoaxify.constraint.password.Pattern.message}")
    private String password;
}
