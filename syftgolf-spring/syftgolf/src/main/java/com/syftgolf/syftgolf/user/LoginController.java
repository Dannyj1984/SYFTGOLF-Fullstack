package com.syftgolf.syftgolf.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.syftgolf.syftgolf.shared.CurrentUser;
import com.syftgolf.syftgolf.user.vm.UserVM;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @PostMapping("/api/1.0/login")
    UserVM handleLogin(@CurrentUser User loggedInUser) {
        return new UserVM(loggedInUser);
    }

}
