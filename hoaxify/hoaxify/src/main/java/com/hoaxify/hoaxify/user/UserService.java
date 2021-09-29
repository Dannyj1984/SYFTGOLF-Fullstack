package com.hoaxify.hoaxify.user;


import com.hoaxify.hoaxify.error.NotFoundException;
import com.hoaxify.hoaxify.user.vm.UserUpdateVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserService {


    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Page<User> getUsers(User loggedInUser, Pageable pageable) {
        if(loggedInUser != null) {
            //Get all users except for logged in user
            return userRepository.findByUsernameNot(loggedInUser.getUsername(), pageable);
        }
        return userRepository.findAll(pageable);
    }

    public User getByUsername(String username) {
        User inDB = userRepository.findByUsername(username);
        if(inDB == null) {
            throw new NotFoundException(username + " not found");
        }
        return inDB;
    }

    public User update(long id, UserUpdateVM userUpdate) {
        User inDB = userRepository.getOne(id);
        inDB.setUsername(userUpdate.getUsername());
        String savedImageName = inDB.getUsername() + UUID.randomUUID().toString().replaceAll("-", "");
        inDB.setImage(savedImageName);
        return userRepository.save(inDB);
    }

    public void deleteMember(long id) {
        User user = userRepository.getOne(id);
        userRepository.deleteById(id);

    }


}
