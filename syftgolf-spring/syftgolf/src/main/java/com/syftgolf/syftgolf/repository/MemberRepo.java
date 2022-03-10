package com.syftgolf.syftgolf.repository;


import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.entity.vm.member.MemberVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepo extends JpaRepository<Member, Long> {



    Member findMemberByUsername(String username);

    Member findMemberById(long id);

    Member findMemberByEmail(String email);

    List<Member> findAllBySocietyIdOrderByUsernameAsc(long id);

    Page<Member> findAllBySocietyIdOrderByUsername(Pageable page, long id);

    Member findMemberBySocietyIdAndUsername(long id, String username);

    Page<Member> findByUsernameStartsWithAndSocietyId(String query, Pageable pageable, long id);

    List<Member> findAllBySocietyIdOrderByFedExPointsDesc(long societyId);
}
