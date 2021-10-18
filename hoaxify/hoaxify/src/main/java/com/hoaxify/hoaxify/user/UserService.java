package com.hoaxify.hoaxify.user;


import com.hoaxify.hoaxify.error.NotFoundException;
import com.hoaxify.hoaxify.file.FileService;
import com.hoaxify.hoaxify.user.vm.UserUpdateVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;


@Service
public class UserService {


    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    FileService fileService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FileService fileService) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSochcp(user.getHandicap());
        return userRepository.save(user);
    }

    public Page<User> getUsers(User loggedInUser, Pageable pageable) {
//        if(loggedInUser != null) {
//            //Get all users except for logged in user
//            return userRepository.findByUsernameNot(loggedInUser.getUsername(), pageable);
//        }
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
        inDB.setHandicap(userUpdate.getHandicap()); // User, getHandicap = String, userupdate getHandicap = String
        System.out.println(inDB.getHandicap());
        double handicapDouble = Double.parseDouble(userUpdate.getHandicap()); //String to double
        System.out.println("soc hcp = " + inDB.getSochcpred());
        System.out.println(handicapDouble);
        int socRedInt = Integer.parseInt(inDB.getSochcpred()); //String to Integer
        System.out.println(socRedInt);
        double newSocHcp = handicapDouble - socRedInt;
        System.out.println(newSocHcp);
        String newSocHcpString = Double.toString(newSocHcp);
        System.out.println(newSocHcpString);
        inDB.setSochcp(newSocHcpString);
        inDB.setEmail(userUpdate.getEmail());
        inDB.setHomeclub(userUpdate.getHomeclub());
        inDB.setMobile(userUpdate.getMobile());
        if(userUpdate.getImage() != null) {
            String savedImageName;
            try {
                savedImageName = fileService.saveProfileImage(userUpdate.getImage());
                inDB.setImage(savedImageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return userRepository.save(inDB);
    }

    public void deleteMember(long id) {
        User user = userRepository.getOne(id);
        userRepository.deleteById(id);

    }


}
