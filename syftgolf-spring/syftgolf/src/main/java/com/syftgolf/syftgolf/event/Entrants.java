package com.syftgolf.syftgolf.event;

import com.syftgolf.syftgolf.user.User;
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

    @Column(name = "score")
    private int score = 0;

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
