package com.syftgolf.syftgolf.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syftgolf.syftgolf.entity.member.Role;
import com.syftgolf.syftgolf.entity.member.UniqueEmail;
import com.syftgolf.syftgolf.entity.member.UniqueUsername;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "entrants")
public class Member implements UserDetails, Serializable {

    @javax.persistence.Transient
    String ROLE_PREFIX = "ROLE_";

    private static final long serialVersionUID = 4074374728582967483L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @NotNull(message = "{syftgolf.constraints.username.NotNull.message}")
    @Size(min = 4, max=255, message = "{javax.validation.constraints.Size.message}")
    @UniqueUsername
    private String username;

    @NotNull(message = "{syftgolf.constraints.name.NotNull.message}")
    @Size(min = 2, max=255, message = "{javax.validation.constraints.Size.message}")
    private String firstName;

    @NotNull(message = "{syftgolf.constraints.name.NotNull.message}")
    @Size(min = 2, max=255, message = "{javax.validation.constraints.Size.message}")
    private String surname;

    @NotNull
    @Size(min = 11, max=11, message = "Please enter a valid mobile number which should be 11 digits long")
    private String mobile;

    @NotNull
    @Size(min = 10, max=10, message = "Please enter a valid CDH number which should be 10 digits long")
    private String cdh;

    @NotNull
    @Column(name = "sochcpred")
    private int socHcpRed = 0;

    @Column(name = "homeclub")
    private String homeClub;

    @NotNull
    @Column(name = "sochcp")
    private double socHcp = 0.0;

    private double handicap;

    @NotNull(message = "{syftgolf.constraints.email.NotNull.message}")
    @Size(min = 6, max=255, message = "{javax.validation.constraints.Size.message}")
    @Email(message = "{syftgolf.constraints.email.invalid.message}")
    @UniqueEmail
    private String email;

    private String image;

    private int eventsPlayed = 0;

    @NotNull
    @Column(
            name = "fedExPoints",
            columnDefinition = "INTEGER"
    )
    private int fedExPoints = 0;

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

    public Member() {}

    public Member(String username, String firstName, String surname, String mobile, String cdh, String homeclub, double handicap, String email, @NotNull @Size(min = 8, max = 255) @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$") String password) {
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.mobile = mobile;
        this.cdh = cdh;
        this.homeClub = homeclub;
        this.socHcp = handicap;
        this.handicap = handicap;
        this.email = email;
        this.password = password;
    }

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

    @JsonIgnore
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<Entrants> entrants;


    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;
}
