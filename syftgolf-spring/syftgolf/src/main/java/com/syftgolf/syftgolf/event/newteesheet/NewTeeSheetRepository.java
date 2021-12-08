package com.syftgolf.syftgolf.event.newteesheet;

import com.syftgolf.syftgolf.event.teesheet.TeeSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewTeeSheetRepository extends JpaRepository<NewTeeSheet, Long> {

    Optional<NewTeeSheet> findById(long id);

    public List<NewTeeSheet> findAllByEvent_Eventid(long id);


}
