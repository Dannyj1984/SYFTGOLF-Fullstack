package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.entity.vm.member.MemberVM;
import com.syftgolf.syftgolf.shared.CurrentUser;
import com.syftgolf.syftgolf.entity.Member;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/api/1.0/login")
    MemberVM handleLogin(@CurrentUser Member loggedInUser) {
        return new MemberVM(loggedInUser);
    }

}
