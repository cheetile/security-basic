package com.dataus.template.securitybasic.member.principal;

import java.util.Collection;
import java.util.Objects;

import com.dataus.template.securitybasic.member.entity.Member;
import com.dataus.template.securitybasic.member.enums.RoleType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
    
    private static final long serialVersionUID = 1L;

    private Long id;

	private String username;
    
	private String password;
    
    private String name;

    private String email;

	private Collection<? extends GrantedAuthority> authorities;

    private boolean deleted;

    public UserDetailsImpl(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.name = member.getName();
        this.email = member.getEmail();
        this.authorities = member.getRoleTypes();
        this.deleted = member.isDeleted();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }

    public boolean hasRole(Long id) {
        return this.id.longValue() == id.longValue() ||
               this.authorities.contains(RoleType.ROLE_ADMIN);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        UserDetailsImpl user = (UserDetailsImpl) obj;
        return Objects.equals(id, user.id);
    }
    
}
