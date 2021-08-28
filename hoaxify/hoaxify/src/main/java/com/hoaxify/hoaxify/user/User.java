package com.hoaxify.hoaxify.user;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.beans.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
//Entity maps object to database
@Entity
@NoArgsConstructor
public class User implements UserDetails {

    private static final long serialVersionUID = 4074374728582967483L;

    @Id
    @GeneratedValue
    @JsonView(Views.Base.class)
    private long id;

    @NotNull(message = "{hoaxify.constraints.username.NotNull.message}")
    @Size(min = 4, max=255)
    @JsonView(Views.Base.class)
    @UniqueUsername
    private String username;

    @NotNull(message = "{hoaxify.constraints.email.NotNull.message}")
    @Size(min = 6, max=255, message = "{javax.validation.constraints.Size.message}")
    @JsonView(Views.Base.class)
    @Email(message = "{hoaxify.constraints.email.invalid.message}")
    @UniqueEmail
    private String email;

    @NotNull(message = "{hoaxify.constraints.name.NotNull.message}")
    @JsonView(Views.Base.class)
    @Size(min = 4, max=255)
    private String firstname;

    @NotNull(message = "{hoaxify.constraints.name.NotNull.message}")
    @Size(min = 4, max=255, message = "{javax.validation.constraints.Size.message}")
    @JsonView(Views.Base.class)
    private String surname;

    @NotNull(message = "Please enter a handicap for this member")
    @Size(min = 1, max=4, message = "Please enter a handicap for this member")
    @JsonView(Views.Base.class)
    private String handicap;

    @NotNull
    @Size(min = 11, max=11, message = "Please enter a valid mobile number which should be 11 digits long")
    @JsonView(Views.Base.class)
    private String mobile;

    @NotNull
    @Size(min = 10, max=10, message = "Please enter a valid CDH number which should be 10 digits long")
    @JsonView(Views.Base.class)
    private String cdh;

    @NotNull
    @JsonView(Views.Base.class)
    private String society_hcp_reduction = "0.0";

    @NotNull
    @JsonView(Views.Base.class)
    private String societyHandicap = "0";

    @NotNull
    @Size(min = 8, max=255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="{hoaxify.constraints.password.Pattern.message}")
    private String password;

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("Role_USER");
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }


}
