package com.syftgolf.syftgolf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "society")
public class Society {

    @Id
    @GeneratedValue
    @Column(name = "society_id")
    private long id;

    @NotNull
    @Column(name = "society_name", unique = true)
    private String name;

    private int noOfMembers;

    public Society(String name){
        this.name = name;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "society", fetch = FetchType.LAZY)
    private List<Member> members;

    @JsonIgnore
    @OneToMany(mappedBy = "society", fetch = FetchType.LAZY)
    private List<Course> courses;

    @JsonIgnore
    @OneToMany(mappedBy = "society", fetch = FetchType.LAZY)
    private List<Event> events;

    @JsonIgnore
    @OneToMany(mappedBy = "society", fetch = FetchType.LAZY)
    private List<Matchplay> Matchplay;

    @JsonIgnore
    @OneToMany(mappedBy = "society", fetch = FetchType.LAZY)
    private List<Tournament> tournaments;
}
