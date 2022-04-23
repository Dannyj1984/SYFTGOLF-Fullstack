package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.MatchPlayer;
import com.syftgolf.syftgolf.entity.Matchplay;
import com.syftgolf.syftgolf.entity.RoundRobin;
import com.syftgolf.syftgolf.entity.vm.event.EventVM;
import com.syftgolf.syftgolf.entity.vm.matchplay.MatchPlayVM;
import com.syftgolf.syftgolf.repository.EventRepo;
import com.syftgolf.syftgolf.repository.MatchPlayRepo;
import com.syftgolf.syftgolf.service.EventService;
import com.syftgolf.syftgolf.service.MatchPlayService;
import com.syftgolf.syftgolf.service.RoundRobinService;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/1.0")
public class MatchPlayController {

    /**
     * Save new matchplay
     * Page of matchplay
     * Get a matchplay
     * Put entrants into groups
     * Get Entrants to an matchplay
     * Delete a matchplay
     * Update matchplay
     * complete a matchplay
     */

    @Autowired
    MatchPlayRepo matchPlayRepo;

    @Autowired
    MatchPlayService matchPlayService;

    @Autowired
    RoundRobinService roundRobinService;

    /**
     *
     * @param societyId id of the society
     * @param matchplay the body to be saved
     * @return a message to inform of successful save
     */
    @PostMapping("/management/matchplay/{societyId:[0-9]+}")
    GenericResponse save(@PathVariable long societyId, @RequestBody Matchplay matchplay ) {
        return matchPlayService.save(societyId, matchplay);
    }

    //Get a page of upcoming matchplay
    @GetMapping("/matchPlays")
    Page<MatchPlayVM> getMatchPlays(Pageable page) {
        return matchPlayService.getMatchPlays(page).map(MatchPlayVM::new);
    }

    @GetMapping("/matchplay/entrants/{matchplayId:[0-9]+}")
    List<MatchPlayer> get(@PathVariable long matchplayId){
        return matchPlayService.get(matchplayId);
    }

    @PutMapping("/management/matchplay/{matchplayId:[0-9]+}")
    Matchplay update(@PathVariable long matchplayId, @RequestBody Matchplay matchplay) {
        return matchPlayService.update(matchplayId, matchplay);
    }

    @PutMapping("/management/matchplay/groups/{matchplayId:[0-9]+}")
    Matchplay createGroups(@PathVariable long matchplayId) {

        return matchPlayService.groups(matchplayId);
    }

    @GetMapping("/matchplay/rounds/{matchplayId:[0-9]+}")
    List<RoundRobin> getMatches(@PathVariable long matchplayId) {
        return roundRobinService.getMatches(matchplayId);
    }

    @PutMapping("/matchplay/round/{matchplayId}")
    void getMatch(@PathVariable long matchplayId){ matchPlayService.createRoundRobin(matchplayId);}


}
