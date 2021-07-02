package com.dataus.template.securitybasic.member.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.dataus.template.securitybasic.member.entity.Member;
import com.dataus.template.securitybasic.member.entity.MemberRole;
import com.dataus.template.securitybasic.member.enums.RoleType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired 
    MemberRoleRepository memberRoleRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void insertMember() throws Exception {
        // given
        Member member1 = new Member("member1", "1234", "kim", "kim@gmail.com");
        Member member2 = new Member("member2", "1234", "lee", "lee@gmail.com");
        Member member3 = new Member("member3", "1234", "park", "park@gmail.com");

        MemberRole member1Role1 = new MemberRole(member1, RoleType.ROLE_USER);
        MemberRole member1Role2 = new MemberRole(member1, RoleType.ROLE_ADMIN);
        MemberRole member2Role1 = new MemberRole(member2, RoleType.ROLE_USER);
        MemberRole member3Role1 = new MemberRole(member3, RoleType.ROLE_USER);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        memberRoleRepository.save(member1Role1);
        memberRoleRepository.save(member1Role2);
        memberRoleRepository.save(member2Role1);
        memberRoleRepository.save(member3Role1);

        em.flush();
        em.clear();
        
        // when
        List<MemberRole> findByRole = memberRoleRepository.findByRole(RoleType.ROLE_USER);
    
        // then
        for (MemberRole memberRole : findByRole) {
            System.out.println(memberRole.getMember());
        }

    }
    
}
