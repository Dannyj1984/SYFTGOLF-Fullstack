package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.entity.MatchPlayer;
import com.syftgolf.syftgolf.service.MatchPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/1.0")
public class MatchPlayerController {

    @Autowired
    MatchPlayerService matchPlayerService;

    /*
    Enter a matchplay event
    Enter a result
     */

    @PostMapping("/matchplayer/{matchPlayId:[0-9]+}/{matchPlayerId:[0-9]+}")
    public List<MatchPlayer> enter(@PathVariable long matchPlayId, @PathVariable long matchPlayerId) {
        return matchPlayerService.enter(matchPlayerId, matchPlayId);
    }
}
