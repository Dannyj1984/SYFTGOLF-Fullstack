package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Matchplay;
import com.syftgolf.syftgolf.entity.RoundRobin;
import com.syftgolf.syftgolf.service.RoundRobinService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoundRobinRepo extends JpaRepository<RoundRobin, Long> {

    List<RoundRobin> findAllByMatchplay(Matchplay matchplay);

}
