package com.syftgolf.syftgolf.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public interface Entrant {

     String getFirstname();

     String getSurname();

     double getHandicap();

     int getScore();
}
