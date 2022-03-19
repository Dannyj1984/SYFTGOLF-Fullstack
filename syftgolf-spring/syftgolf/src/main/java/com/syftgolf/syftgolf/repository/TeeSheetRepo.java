package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.TeeSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeeSheetRepo extends JpaRepository<TeeSheet, Long> {

    List<TeeSheet> findAllByEvent(Event e);

    List<TeeSheet> findAllByEventOrderByTeeTime(Event e);
}
