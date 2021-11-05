package com.hoaxify.hoaxify.event;

import com.hoaxify.hoaxify.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entrants")
public class Entrants {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "entrantid")
    long entrantid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;

    public Entrants(User user, Event event) {
        this.user = user;
        this.event = event;
    }
}
