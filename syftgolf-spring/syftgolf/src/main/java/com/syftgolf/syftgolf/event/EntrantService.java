package com.syftgolf.syftgolf.event;

import com.syftgolf.syftgolf.user.User;
import com.syftgolf.syftgolf.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        
        //TODO need to add 1 to current entrants count for this event
        return entrantRepository.save(entrants);
    }


}
