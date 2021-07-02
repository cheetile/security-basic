package com.dataus.template.securitybasic.member.service;

import javax.transaction.Transactional;

import com.dataus.template.securitybasic.member.dto.MemberResponse;
import com.dataus.template.securitybasic.member.dto.RegisterRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void register() throws Exception {
        // given
        RegisterRequest request = new RegisterRequest("member123", "1234", "kim", "kim@dataus.co.kr");
    
        // when
        MemberResponse response = memberService.register(request);
    
        // then
        System.out.println(response);
        
    
    }
    
}
