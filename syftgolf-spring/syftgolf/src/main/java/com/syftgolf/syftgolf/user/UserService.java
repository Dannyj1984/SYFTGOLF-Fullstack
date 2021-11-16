package com.syftgolf.syftgolf.user;


import com.syftgolf.syftgolf.error.NotFoundException;
import com.syftgolf.syftgolf.file.FileService;
import com.syftgolf.syftgolf.user.vm.UserHandicapVM;
import com.syftgolf.syftgolf.user.vm.UserUpdateHandicapVM;
import com.syftgolf.syftgolf.user.vm.UserUpdateVM;
import com.syftgolf.syftgolf.user.vm.UserUpdateWinVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;


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

    public Page<User> getUsers(Pageable pageable) {
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

    public User updateHandicap(long id, UserUpdateHandicapVM userHandicapVM) {
        User inDB = userRepository.getById(id);
        inDB.setHandicap(userHandicapVM.getHandicap());
        inDB.setSochcpred(userHandicapVM.getSochcpred());

        return userRepository.save(inDB);
    }

    public User addWins(long id) {
        User inDB = userRepository.getById(id);
        inDB.setWins(inDB.getWins() + 1);

        return userRepository.save(inDB);
    }
    public User takeWins(long id) {
        User inDB = userRepository.getById(id);
        inDB.setWins(inDB.getWins() - 1);

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
