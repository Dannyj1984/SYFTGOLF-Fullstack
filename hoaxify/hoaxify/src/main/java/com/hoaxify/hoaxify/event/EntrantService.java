package com.hoaxify.hoaxify.event;

import org.springframework.stereotype.Service;

@Service
public class EntrantService {

    EntrantRepository entrantRepository;

    public EntrantService(EntrantRepository entrantRepository) {
        this.entrantRepository = entrantRepository;
    }

    public Entrants save(Entrants entrant) {
        return entrantRepository.save(entrant);
    }


}
