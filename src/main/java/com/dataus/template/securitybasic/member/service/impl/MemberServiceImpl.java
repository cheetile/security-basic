package com.dataus.template.securitybasic.member.service.impl;

import java.util.Set;

import javax.transaction.Transactional;

import com.dataus.template.securitybasic.common.dto.BaseResponse;
import com.dataus.template.securitybasic.common.exception.ErrorType;
import com.dataus.template.securitybasic.common.utils.JwtUtils;
import com.dataus.template.securitybasic.member.dto.LoginRequest;
import com.dataus.template.securitybasic.member.dto.MemberResponse;
import com.dataus.template.securitybasic.member.dto.ModifyRequest;
import com.dataus.template.securitybasic.member.dto.RegisterRequest;
import com.dataus.template.securitybasic.member.entity.Member;
import com.dataus.template.securitybasic.member.entity.MemberRole;
import com.dataus.template.securitybasic.member.enums.RoleType;
import com.dataus.template.securitybasic.member.repository.MemberRepository;
import com.dataus.template.securitybasic.member.repository.MemberRoleRepository;
import com.dataus.template.securitybasic.member.service.MemberService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    @Override
    public BaseResponse existsUsername(String username) {
        if(!memberRepository.existsByUsername(username)) {
            return BaseResponse.builder()
                    .success(false)
                    .message(String.format("Not Found id: %s", username))
                    .build();
        }

        return BaseResponse.builder()
                .success(true)
                .message(String.format("Found id: %s", username))
                .build();
    }

    @Override
    public MemberResponse login(LoginRequest loginRequest) {
        UserDetails principal = getPrincipal(
            loginRequest.getUsername(), 
            loginRequest.getPassword());
        
        String jwt = jwtUtils.generateJwtToken(principal); 
        
        return MemberResponse.of(principal, jwt);
    }

    @Override
    public MemberResponse register(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        if(existsUsername(username).isSuccess()) {
            throw ErrorType.REGISTERED_ID.getException();
        }

        Member member = memberRepository.save(new Member(
            username,
            passwordEncoder.encode(registerRequest.getPassword()),
            registerRequest.getName(),
            registerRequest.getEmail()));
        
        memberRoleRepository.save(new MemberRole(
            member, 
            RoleType.ROLE_USER));
        memberRoleRepository.save(new MemberRole(
            member, 
            RoleType.ROLE_ADMIN));
        
        UserDetails principal = getPrincipal(
            username, 
            registerRequest.getPassword());
        
        String jwt = jwtUtils.generateJwtToken(principal);
        
        return MemberResponse.of(principal, jwt);
    } 

    private UserDetails getPrincipal(String username, String password) {
        Authentication authentication = authenticationManager
            .authenticate(
                new UsernamePasswordAuthenticationToken(
                    username, 
                    password));
        
        SecurityContextHolder
            .getContext()
            .setAuthentication(authentication);
        
        return (UserDetails) authentication.getPrincipal();
    }

    @Override
    public BaseResponse modifyMember(Long id, ModifyRequest modifyRequest) {
        Member member = memberRepository
            .findById(id)
            .orElseThrow(() ->
                ErrorType.NO_MEMBER_NUM.getException());

        member.modify(modifyRequest);        
        
        return BaseResponse.builder()
                    .success(true)
                    .message(String.format(
                        "Modified Member Id[%s]", 
                        member.getUsername()))
                    .build();
    }

    @Override
    public BaseResponse deleteMember(Long id) {
        Member member = memberRepository
            .findById(id)
            .orElseThrow(() ->
                ErrorType.NO_MEMBER_NUM.getException());

        member.delete();
           
        return BaseResponse.builder()
                    .success(true)
                    .message(String.format(
                        "Deleted Member Id[%s]", 
                        member.getUsername()))
                    .build();
    }

    @Override
    public BaseResponse changeRoles(Long id, Set<RoleType> roles) {
        Member member = memberRepository
            .findById(id)
            .orElseThrow(() ->
                ErrorType.NO_MEMBER_NUM.getException());
        
        member.deleteRoles();
        roles.forEach(r -> 
            memberRoleRepository.save(new MemberRole(member, r)));
        
        return BaseResponse.builder()
            .success(true)
            .message(String.format(
                "Roles of member Id[%s] set to %s", 
                member.getUsername(),
                member.getRoleTypes()))
            .build();
    }

}
