package com.syftgolf.syftgolf.configuration;

import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthUserService implements UserDetailsService {

    @Autowired
    MemberRepo memberRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepo.findMemberByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;

    }

}
