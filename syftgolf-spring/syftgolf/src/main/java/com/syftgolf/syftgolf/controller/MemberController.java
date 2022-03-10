package com.syftgolf.syftgolf.controller;


import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.entity.vm.member.MemberPasswordUpdateVM;
import com.syftgolf.syftgolf.entity.vm.member.MemberUpdateVM;
import com.syftgolf.syftgolf.entity.vm.member.MemberVM;
import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.repository.MemberRepo;
import com.syftgolf.syftgolf.repository.SocietyRepo;
import com.syftgolf.syftgolf.service.MemberService;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/1.0")
public class MemberController {

    /**
     * Create new member
     * Update a member
     * Delete a member
     * Export details to CSV
     * Get a page of members
     * Get a single member
     * Get a list of members by fedex cup position
     * Change member password
     * reset all member wins
     * reset all soc hcp reductions.
     * Get filtered users
     */

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepo memberRepo;

    @Autowired
    SocietyRepo societyRepo;


    
    @PostMapping("/management/member")
    GenericResponse create(@Valid @RequestBody Member member)  {
        memberService.save(member);

        return new GenericResponse("member saved");
    }

    @GetMapping("/getMembers/{societyId:[0-9]+}")
    public List<Member> getListOfMembers(@PathVariable long societyId) {
        return memberRepo.findAllBySocietyIdOrderByUsernameAsc(societyId);
    }

    
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN') || #memberId == principal.id")
    @PutMapping("/updateMember/{memberId:[0-9]+}")
    GenericResponse update(@Valid @RequestBody MemberUpdateVM update, @PathVariable long memberId) {
        return memberService.update(memberId, update);
    }

    
    @DeleteMapping("/management/member/{memberId:[0-9]+}")
    GenericResponse deleteMember(@PathVariable long memberId) {
        Optional<Member> member = memberRepo.findById(memberId);

        if (member.isPresent()) {
            return memberService.deleteMember(memberId);
        } else {

            return new GenericResponse("No member found");
        }
    }

    
    @GetMapping("/management/users/export/{societyId:[0-9]+}")
    public void exportToCSV(HttpServletResponse response, @PathVariable long societyId) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Member> listUsers = memberService.listAll(societyId);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"User ID", "E-mail", "First Name", "Surname", "CDH", "Handicap"};
        String[] nameMapping = {"id", "email", "firstname", "surname", "cdh", "handicap"};

        csvWriter.writeHeader(csvHeader);

        for (Member member : listUsers) {
            csvWriter.write(member, nameMapping);
        }

        csvWriter.close();

    }


    
    @GetMapping("/members/{societyId:[0-9]+}")
    public Page<MemberVM> getMembers(Pageable page, @PathVariable long societyId) {
        return memberService.getMembers(page, societyId).map(MemberVM::new);
    }

    
    @GetMapping("/member/{username:[a-z]+}")
    public MemberVM getMember( @PathVariable String username) {
        Member member = memberService.getMember(username);
        return new MemberVM(member);
    }

    @GetMapping("/members/fedex/{societyId:[0-9]+}")
    public List<MemberVM> getMembersByFedEx(@PathVariable long societyId) {
        return memberService.getMembersByFedEx(societyId);
    }

    
    @PreAuthorize("#id == principal.id")
    @PutMapping("/member/passwordChange/{id:[0-9]+}")
    GenericResponse changePassword(@PathVariable long id, @Valid @RequestBody MemberPasswordUpdateVM memberPasswordUpdate) {
        Member member = memberRepo.findMemberById(id);
        //If the member is part of society 2 (test society) then dont allow them to update the password
        if (member.getSociety().getId() == 2) {
            System.out.println("test user");
            return new GenericResponse("Ah Ah Ah, Dont change test passwords");
        } else {
            memberService.changePassword(id, memberPasswordUpdate);

            return new GenericResponse("Password Changed");
        }
    }

    
    @PutMapping("/management/member/resetWins/{societyId:[0-9]+}")
    GenericResponse resetWins(@PathVariable long societyId) {
        return memberService.resetWins(societyId);
    }

    
    @PutMapping("/management/member/resetHcpRed/{societyId:[0-9]+}")
    GenericResponse resetHandicapReduction(@PathVariable long societyId) {
        return memberService.resetHandicapReduction(societyId);
    }


    
    @GetMapping("/societyFilteredUsers/{id:[0-9]+}")
    Page<Member> getFilteredUsers(Pageable page, @PathVariable long id, @RequestParam String query) {
        return memberService.getFilteredUsers(query, page, id);
    }

    //Make member admin
    @CrossOrigin
    @PutMapping("/management/users/admin/{id:[0-9]+}")
    MemberVM makeAdmin(@PathVariable long id) {
        Member userUpdated = memberService.updateAdmin(id);
        return new MemberVM(userUpdated);
    }

    //Make member handicap admin
    @PutMapping("/management/users/scorer/{id:[0-9]+}")
    MemberVM makeHcpAdmin(@PathVariable long id) {
        Member memberUpdated = memberService.updateScorer(id);
        return new MemberVM(memberUpdated);
    }

    //Make member eventadmin
    @PutMapping("/management/users/EventAdmin/{id:[0-9]+}")
    MemberVM makeEventAdmin(@PathVariable long id) {
        Member userUpdated = memberService.updateEventAdmin(id);
        return new MemberVM(userUpdated);
    }

    //Make member a user
    @PutMapping("/management/users/User/{id:[0-9]+}")
    MemberVM makeUser(@PathVariable long id) {
        Member userUpdated = memberService.updateUser(id);
        return new MemberVM(userUpdated);
    }


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







