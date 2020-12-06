package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() {

        //given
        Member member = new Member();
        member.setUsername("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test
    public void 중복_회원_예외() throws Exception {

        //given
        Member member1 = new Member();
        member1.setUsername("kim1");

        Member member2 = new Member();
        member2.setUsername("kim1");

        // when
        memberService.join(member1);

        // then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals(exception.getMessage(), "이미 존재하는 회원입니다.");

    }

}