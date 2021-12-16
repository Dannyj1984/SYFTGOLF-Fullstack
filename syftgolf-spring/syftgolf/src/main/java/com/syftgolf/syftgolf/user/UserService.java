package com.syftgolf.syftgolf.user;


import com.syftgolf.syftgolf.error.NotFoundException;
import com.syftgolf.syftgolf.file.FileService;
import com.syftgolf.syftgolf.society.Society;
import com.syftgolf.syftgolf.society.SocietyRepository;
import com.syftgolf.syftgolf.user.vm.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class UserService {


    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    SocietyRepository societyRepository;

    FileService fileService;

    public UserService(UserRepository userRepository, SocietyRepository societyRepository, PasswordEncoder passwordEncoder, FileService fileService) {
        super();
        this.userRepository = userRepository;
        this.societyRepository = societyRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
    }

    public User save(User user) {
        String uname = user.getUsername();
        String trimmed = uname.trim();
        user.setUsername(trimmed);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSochcp(user.getHandicap());
        Society inDB = societyRepository.getOne(user.getSociety().getId());
        user.setSociety(inDB);
        return userRepository.save(user);
    }

    //Get list of users by society and sort by username for exporting
    public List<User> listAll(long id) {
        return userRepository.findAllBySocietyId(id, Sort.by("username").ascending());
    }

    public User changePassword(long userid, UserPasswordUpdateVM userPasswordUpdateVM) {
        User inDB = userRepository.getOne(userid);
        inDB.setPassword(passwordEncoder.encode(userPasswordUpdateVM.getPassword()));
        System.out.println(userPasswordUpdateVM.getPassword());
        return userRepository.save(inDB);
    }

    public Page<User> getUsers(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    public Page<User> getSocietyUsers(Pageable pageable, long id) {

        return userRepository.findAllSocietyUsers(pageable, id);
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
        inDB.setCdh(userUpdate.getCdh());
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

    public Page<User> getSomeUsers(Pageable pageable, long id) {
        return userRepository.findAllBySocietyId(pageable, id);
    }

    public Page<User> getFilteredUsers(String query, Pageable pageable, long id) {
        return userRepository.findByUsernameStartsWithAndSocietyId(query, pageable, id);
    }


}
