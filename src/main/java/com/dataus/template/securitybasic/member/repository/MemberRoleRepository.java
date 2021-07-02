package com.dataus.template.securitybasic.member.repository;

import java.util.List;

import com.dataus.template.securitybasic.member.entity.MemberRole;
import com.dataus.template.securitybasic.member.enums.RoleType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    List<MemberRole> findByRole(RoleType role);
    
}
