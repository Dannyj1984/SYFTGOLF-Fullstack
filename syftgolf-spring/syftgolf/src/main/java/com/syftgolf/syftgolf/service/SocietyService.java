package com.syftgolf.syftgolf.service;

import com.syftgolf.syftgolf.entity.Society;
import com.syftgolf.syftgolf.repository.SocietyRepo;
import org.springframework.stereotype.Service;

@Service
public class SocietyService {

    SocietyRepo societyRepo;

    public SocietyService(SocietyRepo societyRepo) {
        this.societyRepo = societyRepo;
    }

    //Save a new society
    public Society save(Society society) {
        return societyRepo.save(society);
    }
}
