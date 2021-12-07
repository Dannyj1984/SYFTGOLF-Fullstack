package com.syftgolf.syftgolf.event.teesheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syftgolf.syftgolf.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teesheet")
public class TeeSheet {
    @Id
    @Column(name = "teesheetid")
    private long id;


    //Tee sheet
    private String teetime1;
    private String teetime2;
    private String teetime3;
    private String teetime4;

    private String p1t1;
    private String p2t1;
    private String p3t1;
    private String p4t1;

    private String p1t2;
    private String p2t2;
    private String p3t2;
    private String p4t2;

    private String p1t3;
    private String p2t3;
    private String p3t3;
    private String p4t3;

    private String p1t4;
    private String p2t4;
    private String p3t4;
    private String p4t4;

    @JsonIgnore
    @OneToOne(mappedBy = "teeSheet")
    private Event event;


    public TeeSheet(TeeSheet teeSheet) {
    }
}
