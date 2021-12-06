package com.syftgolf.syftgolf.event.teesheet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeeSheetRepository extends JpaRepository<TeeSheet, Long> {

    Optional<TeeSheet> findById(long id);

}
