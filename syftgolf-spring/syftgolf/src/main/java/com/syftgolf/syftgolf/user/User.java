package com.syftgolf.syftgolf.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.syftgolf.syftgolf.society.Society;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.beans.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Data
//Entity maps object to database
@Entity
@NoArgsConstructor
@ToString
@Table(name = "member")
public class User implements UserDetails, Serializable {

    @javax.persistence.Transient
    String ROLE_PREFIX = "ROLE_";

    private static final long serialVersionUID = 4074374728582967483L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(
            name = "userid",
            updatable = false
    )
    private long id;

    @NotNull(message = "{syftgolf.constraints.username.NotNull.message}")
    @Size(min = 4, max=255, message = "{javax.validation.constraints.Size.message}")
    @UniqueUsername
    private String username;

    @NotNull(message = "{syftgolf.constraints.email.NotNull.message}")
    @Size(min = 6, max=255, message = "{javax.validation.constraints.Size.message}")
    @Email(message = "{syftgolf.constraints.email.invalid.message}")
    @UniqueEmail
    private String email;

    @NotNull(message = "{syftgolf.constraints.name.NotNull.message}")
    @Size(min = 2, max=255, message = "{javax.validation.constraints.Size.message}")
    private String firstname;

    @NotNull(message = "{syftgolf.constraints.name.NotNull.message}")
    @Size(min = 2, max=255, message = "{javax.validation.constraints.Size.message}")
    private String surname;

    @NotNull(message = "Please enter a handicap for this member")
    private double handicap;

    @NotNull
    @Size(min = 11, max=11, message = "Please enter a valid mobile number which should be 11 digits long")
    private String mobile;

    @NotNull
    @Size(min = 10, max=10, message = "Please enter a valid CDH number which should be 10 digits long")
    private String cdh;

    @NotNull
    @Column(name = "sochcpred")
    private int sochcpred = 0;

    @Column(name = "homeclub")
    private String homeclub;

    @NotNull
    @Column(name = "sochcp")
    private double sochcp = 0.0;

    private String image;

    @NotNull
    @Column(
            name = "wins",
            columnDefinition = "INTEGER"
    )
    private int wins = 0;

    @NotNull
    @Size(min = 8, max=255 , message = "{javax.validation.constraints.Size.message}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message="{syftgolf.constraints.password.Pattern.message}")
    private String password;

    @NotNull
    @Column(
            name = "role"
    )
    private String role = "USER";


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> list = new ArrayList<>();


            list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));


        return list;
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "roleid")
    )
    private Set<Role> newroles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;


}
