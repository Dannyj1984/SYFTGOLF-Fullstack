package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.TeeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeeSheetRepo extends JpaRepository<TeeSheet, Long> {

    List<TeeSheet> findAllByEvent(Event e);

    List<TeeSheet> findAllByEventOrderByTeeTime(Event e);

    @Modifying
    @Transactional
    @Query(value = "update tee_sheet_entrants set entrants_member_id = :memberId where tee_sheet_id = :teeSheetId AND entrants_member_id = :prevMemberId", nativeQuery = true)
        void update(long teeSheetId, long memberId, long prevMemberId);

}
