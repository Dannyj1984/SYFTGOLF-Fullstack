package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Society;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocietyRepo extends JpaRepository<Society, Long> {

    Society findAllByName(String name);
}
