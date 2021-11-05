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
    public User updateAdmin(long id){
        User inDB = userRepository.getById(id);
        inDB.setRole("ADMIN");

        return userRepository.save(inDB);
    }

    public User updateUser(long id){
        User inDB = userRepository.getById(id);
        inDB.setRole("USER");

        return userRepository.save(inDB);
    }

    public User updateHcpAdmin(long id){
        User inDB = userRepository.getById(id);
        inDB.setRole("HANDICAPADMIN");

        return userRepository.save(inDB);
    }

    public User updateEventAdmin(long id){
        User inDB = userRepository.getById(id);
        inDB.setRole("EVENTADMIN");

        return userRepository.save(inDB);
    }

    public User update(long id, UserUpdateVM userUpdate) {
        User inDB = userRepository.getOne(id);
        inDB.setUsername(userUpdate.getUsername());
        inDB.setHandicap(userUpdate.getHandicap()); // User, getHandicap = String, userupdate getHandicap = String
        inDB.setSochcp(userUpdate.getHandicap() - inDB.getSochcpred());
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
