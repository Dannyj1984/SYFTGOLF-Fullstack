package com.syftgolf.syftgolf.entity.vm.hole;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class HoleVM {

    private int holeNumber;
    private int strokeIndex;
    private int yards;
    private int par;
    @Column(
            name = "holeIdentifier",
            unique = true
    )
    private String holeIdentifier;
}
