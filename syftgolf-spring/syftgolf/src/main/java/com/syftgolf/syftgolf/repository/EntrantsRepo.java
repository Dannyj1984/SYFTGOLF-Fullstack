package com.syftgolf.syftgolf.repository;


import com.syftgolf.syftgolf.entity.Entrants;
import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EntrantsRepo extends JpaRepository<Entrants, Long> {

    Entrants getEntrantsByMemberAndEvent(Member member, Event event);

    List<Entrants> findAllByEvent(Event event);

    @Transactional
    void deleteByMemberAndEvent(Member m, Event e);

        @Query(value = "delete from tee_sheet_entrants where tee_sheet_entrants.entrants_event_id = :eventId and tee_sheet_entrants.entrants_member_id = :memberId", nativeQuery = true)
        void deleteTeeSheetEntrant(long eventId, long memberId);
}
