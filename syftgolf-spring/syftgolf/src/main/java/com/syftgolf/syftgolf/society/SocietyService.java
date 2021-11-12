package com.syftgolf.syftgolf.society;

import org.springframework.stereotype.Service;

@Service
public class SocietyService {

    SocietyRepository societyRepository;

    public SocietyService(SocietyRepository societyRepository) {
        this.societyRepository = societyRepository;
    }

    public Society save(Society society) {
        return societyRepository.save(society);
    }
}
