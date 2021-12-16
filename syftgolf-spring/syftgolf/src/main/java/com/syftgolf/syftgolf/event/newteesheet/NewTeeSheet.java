package com.syftgolf.syftgolf.event.newteesheet;


import com.syftgolf.syftgolf.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull(message = "{syftgolf.constraints.teesheet.NotNull.message}")
    @Size(min = 3, max = 8, message = "Please enter a valid time in the format hh : mm")
    private String teetime;

    private String player1;

    private String player2;

    private String player3;

    private String player4;

    @ManyToOne
    @JoinColumn(name = "eventid_id")
    private Event event;
}
