package com.hoaxify.hoaxify.event;

import com.hoaxify.hoaxify.course.EntrantId;
import com.hoaxify.hoaxify.course.HoleId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EntrantId.class)
public class Entrants implements Serializable {

    @NotNull
    @Id
    private int userid;

    @NotNull
    @Id
    private int eventid;
}
