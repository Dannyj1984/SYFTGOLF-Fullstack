package com.syftgolf.syftgolf.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class MatchPlayerId implements Serializable {

    private long member;
    private long matchplay;
}
