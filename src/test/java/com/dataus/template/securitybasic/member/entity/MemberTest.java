package com.dataus.template.securitybasic.member.entity;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.dataus.template.securitybasic.member.enums.RoleType;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void insertMember() throws Exception {
        // given
        Member member1 = new Member("mem123", "1234", "kim", "kim@gmail.com"); 
        MemberRole memberRole1 = new MemberRole(member1, RoleType.ROLE_USER);
        MemberRole memberRole2 = new MemberRole(member1, RoleType.ROLE_ADMIN);
    
        // when
        em.persist(member1);
        em.persist(memberRole1);        
        em.persist(memberRole2);        
    
        // then
        Assertions.assertThat(member1.getUsername()).isEqualTo("mem123");
        Assertions.assertThat(member1.getName()).isEqualTo("kim");
        Assertions.assertThat(memberRole1.getMember()).isEqualTo(member1);
        Assertions.assertThat(memberRole1.getRole()).isEqualTo(RoleType.ROLE_USER);
    
    }
    
}
