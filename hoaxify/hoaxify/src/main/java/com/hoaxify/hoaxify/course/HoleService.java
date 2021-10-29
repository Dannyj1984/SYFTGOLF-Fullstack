package com.hoaxify.hoaxify.course;

import org.springframework.stereotype.Service;

@Service
public class HoleService {

    HoleRepository holeRepository;

    public HoleService(HoleRepository holeRepository) {
        this.holeRepository = holeRepository;
    }

    public Hole save(Hole hole) {;
        return holeRepository.save(hole);
    }
}
