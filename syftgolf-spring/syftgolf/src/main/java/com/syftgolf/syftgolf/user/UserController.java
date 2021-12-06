package com.syftgolf.syftgolf.user;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.syftgolf.syftgolf.shared.CurrentUser;
import com.syftgolf.syftgolf.society.Society;
import com.syftgolf.syftgolf.society.SocietyRepository;
import com.syftgolf.syftgolf.user.vm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@RestController
@RequestMapping("/api/1.0")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SocietyRepository societyRepository;


    //Create new member
    @CrossOrigin
    @PostMapping("/management/users")
    GenericResponse createUser(@Valid @RequestBody User user) {
        userService.save(user);
        //Get the society this user will be added to
        Society soc = societyRepository.getOne(user.getSociety().getId());

        //Increase the number of members for this society by 1
        soc.setMembers(soc.getMembers() + 1);

        //Save the society with updated members
        societyRepository.save(soc);
        return new GenericResponse("User saved");
    }
    //Export CSV of users
    @GetMapping("/users/export/{id:[0-9]+}")
    public void exportToCSV(HttpServletResponse response, @PathVariable long id) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userService.listAll(id);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"User ID", "E-mail", "First Name", "Surname", "CDH", "Handicap"};
        String[] nameMapping = {"id", "email", "firstname", "surname", "cdh", "handicap"};

        csvWriter.writeHeader(csvHeader);

        for (User user : listUsers) {
            csvWriter.write(user, nameMapping);
        }

        csvWriter.close();

    }

    //Get Page of users for a society.
    @CrossOrigin
    @GetMapping("/societyUsers/{id:[0-9]+}")
    Page<User> getSomeUsers(Pageable page, @PathVariable long id) {
        return userService.getSomeUsers(page, id);
    }

    //Get page of users for a society filtered by username containing
    @CrossOrigin
    @GetMapping("/societyFilteredUsers/{id:[0-9]+}")
    Page<User> getFilteredUsers(Pageable page, @PathVariable long id, @RequestParam String query) {
        return userService.getFilteredUsers(query, page, id);
    }

    //Get member by username
    @CrossOrigin
    @GetMapping("/users/{username}")
    UserVM getUserByName(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return new UserVM(user);
    }

    //Edit member details
    @PutMapping("/management/users/{id:[0-9]+}")
    @PreAuthorize("#id == principal.id")
    @CrossOrigin
    UserVM updateUser(@PathVariable long id, @Valid @RequestBody(required = false) UserUpdateVM userUpdate) {
        User updated = userService.update(id, userUpdate);
        return new UserVM(updated);
    }

    //Delete member
    @CrossOrigin
    @DeleteMapping("/management/users/delete/{id:[0-9]+}")
    GenericResponse deleteMember(@PathVariable long id) {
        //Get the user from the database
        User u = userRepository.getOne(id);

        //Get the society this user belongs to
        Society s = societyRepository.getOne(u.getSociety().getId());

        //Take one from the number of members of this society
        s.setMembers(s.getMembers() - 1);

        //Delete the user from database
        userService.deleteMember(id);

        //Save the society with the updated number of members
        societyRepository.save(s);
        return new GenericResponse("Member has been removed");
    }

    //Make member admin
    @CrossOrigin
    @PutMapping("/management/users/admin/{id:[0-9]+}")
    UserVM makeAdmin(@PathVariable long id) {
        User userUpdated = userService.updateAdmin(id);
        return new UserVM(userUpdated);
    }

    //Make member handicap admin
    @CrossOrigin
    @PutMapping("/management/users/HcpAdmin/{id:[0-9]+}")
    UserVM makeHcpAdmin(@PathVariable long id) {
        User userUpdated = userService.updateHcpAdmin(id);
        return new UserVM(userUpdated);
    }

    //Make member eventadmin
    @CrossOrigin
    @PutMapping("/management/users/EventAdmin/{id:[0-9]+}")
    UserVM makeEventAdmin(@PathVariable long id) {
        User userUpdated = userService.updateEventAdmin(id);
        return new UserVM(userUpdated);
    }

    //Make member a user
    @CrossOrigin
    @PutMapping("/management/users/User/{id:[0-9]+}")
    UserVM makeUser(@PathVariable long id) {
        User userUpdated = userService.updateUser(id);
        return new UserVM(userUpdated);
    }

    //Edit members handicap
    @CrossOrigin
    @PutMapping("/management/users/handicap/{id:[0-9]+}")
    UserHandicapVM updateHandicap(@PathVariable long id, @Valid @RequestBody(required = false) UserUpdateHandicapVM userUpdate) {
        User updated = userService.updateHandicap(id, userUpdate);
        return new UserHandicapVM(updated);
    }

    //Edit members win count add one
    @CrossOrigin
    @PutMapping("/management/user/win/{id:[0-9]+}")
    UserHandicapVM updateWins(@PathVariable long id) {
        User updated = userService.addWins(id);
        return new UserHandicapVM(updated);
    }

    //Edit members win count take one
    @CrossOrigin
    @PutMapping("/management/user/takeWin/{id:[0-9]+}")
    UserHandicapVM takeWins(@PathVariable long id) {
        User updated = userService.takeWins(id);
        return new UserHandicapVM(updated);
    }

    //Update password
    @CrossOrigin
    @PreAuthorize("#id == principal.id")
    @PutMapping("/user/passwordChange/{id:[0-9]+}")
    GenericResponse changePassword(@PathVariable long id, @Valid @RequestBody UserPasswordUpdateVM userPasswordUpdate) {
        User user = userRepository.getOne(id);
        if (user.getSociety().getId() == 2) {
            System.out.println("test user");
            return new GenericResponse("Ah Ah Ah, Dont change test passwords");
        } else {
            userService.changePassword(id, userPasswordUpdate);

            return new GenericResponse("Password Changed");
        }
    }

    //get List of members who have entered an event
    @CrossOrigin
    @GetMapping("/users/events/entrants")
    List<User> entrants(){
        return userRepository.findAllUsers(Sort.by("username"));
    }

    //get List of members
//    @CrossOrigin
//    @GetMapping("/getListOfUser")
//    List<User> users(){
//        return userRepository.findAllUsers(Sort.by("username"));
//    }



    //Get page of members
//    @CrossOrigin
//    @GetMapping("/users")
//    Page<UserVM> getUsers(Pageable page) {
//        return userService.getUsers(page).map(UserVM::new);
//    }















    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError(400, "Validation error", request.getServletPath());

        BindingResult result = exception.getBindingResult();

        Map<String, String> validationErrors = new HashMap<>();

        for(FieldError fieldError: result.getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);

        return apiError;
    }



}







