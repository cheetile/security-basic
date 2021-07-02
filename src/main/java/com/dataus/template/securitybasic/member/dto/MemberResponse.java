package com.dataus.template.securitybasic.member.dto;

import java.util.Collection;

import com.dataus.template.securitybasic.member.entity.Member;
import com.dataus.template.securitybasic.member.principal.UserDetailsImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@JsonPropertyOrder({
    "id", "username", "name",
    "email", "roles", "jwt"
})
public class MemberResponse {

    private Long id;

    private String username;

    private String name;

    private String email;

    private Collection<? extends GrantedAuthority> roles;

    @JsonInclude(Include.NON_NULL)
    private String jwt;
    

    public static MemberResponse of(Member member) {
        return MemberResponse.of(member, null);
    }

    public static MemberResponse of(Member member, String jwt) {
        return MemberResponse.builder()
                    .id(member.getId())
                    .username(member.getUsername())
                    .name(member.getName())
                    .email(member.getEmail())
                    .roles(member.getRoleTypes())
                    .jwt(jwt)
                    .build();
    }

    public static MemberResponse of(UserDetails principal) {
        return MemberResponse.of(principal, null);
    }

    public static MemberResponse of(UserDetails principal, String jwt) {
        
        UserDetailsImpl user = (UserDetailsImpl) principal;

        return MemberResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .name(user.getName())
                    .email(user.getEmail())
                    .roles(user.getAuthorities())
                    .jwt(jwt)
                    .build();
    }
    
}
