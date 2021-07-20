package com.hoaxify.hoaxify.user;

import com.hoaxify.hoaxify.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//Responsible for HTTP requests
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/v1.0/users")
    GenericResponse createUser(@RequestBody User user){
        userService.save(user);
        return new GenericResponse("User added");
    }

}
