package com.dataus.template.securitybasic.member.service.impl;

import com.dataus.template.securitybasic.member.entity.Member;
import com.dataus.template.securitybasic.member.principal.UserDetailsImpl;
import com.dataus.template.securitybasic.member.repository.MemberRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> 
                    new UsernameNotFoundException(String.format(
                        "Member Not Found with id: %s", username)));
        
        return new UserDetailsImpl(member);
    }
    
}
