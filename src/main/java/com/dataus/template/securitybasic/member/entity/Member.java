package com.dataus.template.securitybasic.member.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dataus.template.securitybasic.common.entity.BaseEntity;
import com.dataus.template.securitybasic.member.dto.ModifyRequest;
import com.dataus.template.securitybasic.member.enums.RoleType;

import org.hibernate.annotations.Where;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NamedEntityGraph(
    name = "Member.role",
    attributeNodes = @NamedAttributeNode("roles"))
@Entity
@Table(name = "tb_members")
@Where(clause = "del_yn = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String name;

    @Column(length = 320)
    private String email;

    @OneToMany(mappedBy = "member")
    private List<MemberRole> roles = new ArrayList<>();

    public Member(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    @Override
    public void delete() {
        this.roles.forEach(MemberRole::delete);
        this.setDeleted(true);
    }

    public void modify(ModifyRequest modifyRequest) {
        this.name = modifyRequest.getName();
        this.email = modifyRequest.getEmail();
    }
    
    public Set<RoleType> getRoleTypes() {
        return this.roles.stream()
                .map(MemberRole::getRole)
                .collect(Collectors.toSet());
    }

    public void deleteRoles() {
        this.roles.forEach(MemberRole::delete);
        this.roles.clear();
    }

}
