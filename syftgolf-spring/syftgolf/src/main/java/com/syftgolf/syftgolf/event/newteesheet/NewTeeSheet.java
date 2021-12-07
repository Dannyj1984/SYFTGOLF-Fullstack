package com.syftgolf.syftgolf.event.newteesheet;


import com.syftgolf.syftgolf.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "newteesheet")
public class NewTeeSheet {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "teesheetid")
    private long id;

    @NotNull
    private String teetime;

    @NotNull
    private String player1;

    private String player2;

    private String player3;

    private String player4;

    @ManyToOne
    @JoinColumn(name = "eventid_id")
    private Event event;
}
