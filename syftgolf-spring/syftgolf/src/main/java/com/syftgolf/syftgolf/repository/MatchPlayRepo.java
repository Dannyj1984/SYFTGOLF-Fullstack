package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.Matchplay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface MatchPlayRepo extends JpaRepository<Matchplay, Long> {

    Matchplay findMatchplayById(long id);

    Page<Matchplay> findAll(Pageable pageable);
}
