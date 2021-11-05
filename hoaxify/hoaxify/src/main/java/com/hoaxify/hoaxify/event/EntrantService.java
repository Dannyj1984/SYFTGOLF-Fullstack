package com.hoaxify.hoaxify.event;

import com.hoaxify.hoaxify.user.User;
import com.hoaxify.hoaxify.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntrantService{

    @Autowired
    EntrantRepository entrantRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;



    public Entrants save(EntrantsDto entrant) {
        User user = this.userRepository.findById(entrant.getUser_id()).orElseThrow(IllegalArgumentException::new);
        System.out.println(user.getId());
        Event event = this.eventRepository.findById(entrant.getEvent_id()).orElseThrow(IllegalArgumentException::new);
        System.out.println(event.getEventid());
        Entrants entrants = new Entrants(user, event);
        return entrantRepository.save(entrants);
    }


}
