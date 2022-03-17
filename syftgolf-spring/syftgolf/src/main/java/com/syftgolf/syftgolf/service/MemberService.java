package com.syftgolf.syftgolf.service;


import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.entity.Society;
import com.syftgolf.syftgolf.entity.vm.member.MemberPasswordUpdateVM;
import com.syftgolf.syftgolf.entity.vm.member.MemberUpdateHandicapVM;
import com.syftgolf.syftgolf.entity.vm.member.MemberUpdateVM;
import com.syftgolf.syftgolf.entity.vm.member.MemberVM;
import com.syftgolf.syftgolf.repository.MemberRepo;
import com.syftgolf.syftgolf.repository.SocietyRepo;
import com.syftgolf.syftgolf.service.file.FileService;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MemberService {

    /**
     * Save new member
     * Update member details
     * Change user password
     * List all members
     * Get page of all members
     * Delete member
     * Reset wins
     * Reset handicap reduction
     */

    MemberRepo memberRepo;
    SocietyRepo societyRepo;
    FileService fileService;

    PasswordEncoder passwordEncoder;

    public MemberService(MemberRepo memberRepo, PasswordEncoder passwordEncoder, SocietyRepo societyRepo, FileService fileService) {
        super();
        this.memberRepo = memberRepo;
        this.passwordEncoder = passwordEncoder;
        this.societyRepo = societyRepo;
        this.fileService = fileService;
    }

    /**
     *
     * @param member to be saved
     */
    public Member save(Member member) {
        System.out.println(member);
        //Encode the password for the new member and save

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setSocHcp(member.getHandicap());

        //Get the society the new member belongs to and add one to the number of members
        Society society1 = societyRepo.getById(member.getSociety().getId());
        member.setSociety(society1);
        societyRepo.findById(member.getSociety().getId())
                .ifPresent(society -> {
                    society.setNoOfMembers(society.getNoOfMembers() + 1);
                    societyRepo.save(society);
                });

        List<Member> currentMembers = society1.getMembers();
        currentMembers.add(member);
        return memberRepo.save(member);
    }

    /**
     *
     * @param memberId id of the member to update
     * @param update the body to update
     * @return a message saying successful
     */
    public GenericResponse update(long memberId, MemberUpdateVM update) {
        memberRepo.findById(memberId)
                .ifPresent(member -> {
                    member.setUsername(update.getUsername());
                    member.setHandicap((update.getHandicap()));
                    member.setEmail(update.getEmail());
                    member.setMobile(update.getMobile());
                    member.setHomeClub(update.getHomeClub());
                    member.setCdh(update.getCdh());
                    if(update.getImage() != null) {
                        String savedImageName;
                        try {
                            savedImageName = fileService.saveProfileImage(update.getImage());
                            member.setImage(savedImageName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    memberRepo.save(member);
                });
        return new GenericResponse("Member details updated");
    }

    /**
     *
     * @param userid id of the user to change the password of
     * @param memberPasswordUpdateVM the password to change to
     */
    public void changePassword(long userid, MemberPasswordUpdateVM memberPasswordUpdateVM) {
        memberRepo.findById(userid)
                .ifPresent(member -> {
                    member.setPassword(passwordEncoder.encode(memberPasswordUpdateVM.getPassword()));
                    memberRepo.save(member);
                });
    }

    /**
     *
     * @param id of the society to get members from
     * @return a list of members
     */
    //Get list of users by society and sort by username for exporting
    public List<Member> listAll(long id) {
        return memberRepo.findAllBySocietyIdOrderByUsernameAsc(id);
    }


    /**
     *
     * @param pageable Page object
     * @param id of the society
     * @return a page of members
     */
    public Page<Member> getMembers(Pageable pageable, long id) {
        return memberRepo.findAllBySocietyIdOrderByUsername(pageable, id);
    }


    /**
     *
     * @param username of the member
     * @return the member
     */
    public Member getMember( String username) {
        return memberRepo.findMemberByUsername(username);
    }


    /**
     *
     * @param memberId of the member to be deleted
     */
    public GenericResponse deleteMember(long memberId) {
        //Get the user from the database
        Member member = memberRepo.findMemberById(memberId);

        //Get the society this user belongs to
        Society s = societyRepo.getOne(member.getSociety().getId());

        //Take one from the number of members of this society
        s.setNoOfMembers(s.getNoOfMembers() - 1);

        societyRepo.save(s);
        memberRepo.delete(member);
        return new GenericResponse("Member deleted");

    }

    /**
     *
     * @param societyId if of the society to get members fromt
     * @return a message of successful reset
     */
    public GenericResponse resetWins(long societyId) {
        List<Member> members =  memberRepo.findAllBySocietyIdOrderByUsernameAsc(societyId);
        members.forEach(member -> {
            member.setWins(0);
            memberRepo.save(member);
            System.out.println(member.getFirstName() + " " + member.getWins());
        });
        return new GenericResponse("Wins reset for all members");
    }

    /**
     *
     * @param societyId if of the society to get members fromt
     * @return a message of successful reset
     */
    public GenericResponse resetHandicapReduction(long societyId) {
        List<Member> members =  memberRepo.findAllBySocietyIdOrderByUsernameAsc(societyId);
        members.forEach(member -> {
            member.setSocHcpRed(0);
            memberRepo.save(member);
        });
        return new GenericResponse("Handicap reduction reset for all members");
    }

    public Member updateAdmin(long id){
        Member inDB = memberRepo.getById(id);
        inDB.setRole("ADMIN");

        return memberRepo.save(inDB);
    }

    public Member updateUser(long id){
        Member inDB = memberRepo.getById(id);
        inDB.setRole("USER");

        return memberRepo.save(inDB);
    }

    public Member updateScorer(long id){
        Member inDB = memberRepo.getById(id);
        inDB.setRole("SCORER");

        return memberRepo.save(inDB);
    }

    public Member updateEventAdmin(long id){
        Member inDB = memberRepo.getById(id);
        inDB.setRole("EVENTADMIN");

        return memberRepo.save(inDB);
    }

    public Page<Member> getFilteredUsers(String query, Pageable pageable, long id) {
        return memberRepo.findByUsernameStartsWithAndSocietyId(query, pageable, id);
    }


    public List<MemberVM> getMembersByFedEx(long societyId) {
        List<Member> members = memberRepo.findAllBySocietyIdOrderByFedExPointsDesc(societyId);
        List<MemberVM> memberVM = new ArrayList<>();
        for(Member m : members) {
            memberVM.add(new MemberVM(m));
        }
        return memberVM;
    }

    public GenericResponse updateHandicap(long memberId, MemberUpdateHandicapVM member) {
        Member m = memberRepo.findMemberById(memberId);
        m.setHandicap(member.getHandicap());
        m.setSocHcpRed(member.getSocHcpRed());
        memberRepo.save(m);
        return new GenericResponse("Handicap updated to " + m.getHandicap() + " for " + m.getFirstName() + " " + m.getSurname());
    }
}
