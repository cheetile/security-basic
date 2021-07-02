package com.dataus.template.securitybasic.member.enums;

import java.util.Arrays;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleType implements GrantedAuthority {
    
    ROLE_USER("01", "사용자"),
    ROLE_ADMIN("02", "관리자");

    private final String code;
    private final String description;

    public static RoleType ofCode(String code) {
        return Arrays.stream(RoleType.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> 
                    new RuntimeException(String.format(
                        "Not Found Code=[%s] Role", 
                        code)));
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
    
}
