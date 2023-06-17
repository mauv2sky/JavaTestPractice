package com.example.javatestpractice.mockito;

import com.example.javatestpractice.domain.Member;
import com.example.javatestpractice.domain.Study;
import com.example.javatestpractice.repository.StudyRepository;
import com.example.javatestpractice.service.MemberService;
import com.example.javatestpractice.service.StudyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


/**
 * stub는 필요한 인터페이스에 대한 구현 객체로,
 * 마치 실제로 동작하는 것처럼 보이게 만들어 놓은 객체를 뜻한다.
 * */
@ExtendWith(MockitoExtension.class)
public class StubbingTest {

    @Test
    void createNewStudyService(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("mauv2sky@gmail.com");

        // findById() 매개변수에 따라 리턴 받을 객체를 달리할 때 사용
        // 매개변수를 구분하지 않고 객체를 리턴 받고 싶다면 any()를 사용하면 됨
        Mockito.when(memberService.findById(Mockito.any())).thenReturn(Optional.of(member));

        assertEquals("mauv2sky@gmail.com", memberService.findById(1L).get().getEmail());
        assertEquals("mauv2sky@gmail.com", memberService.findById(2L).get().getEmail());

        // 예외 던지는 방법 1
        Mockito.doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        // 방법 2
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });

        memberService.validate(2L);
    }

    @Test
    void createNewStudyService1(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("mauv2sky@gmail.com");

        Mockito.when(memberService.findById(Mockito.any()))
                .thenReturn(Optional.of(member))       // 첫번째 호출
                .thenThrow(new RuntimeException())     // 두번째 호출
                .thenReturn(Optional.empty());    // 세번째 호출

        Optional<Member> byId = memberService.findById(1L);
        assertEquals("mauv2sky@gmail.com", byId.get().getEmail());

        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });

        assertEquals(Optional.empty(), memberService.findById(3L));
    }

    @Test
    void createNewStudyService3(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("ahyeon@mail.com");

        Study study = new Study(10, "테스트");

        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Mockito.when(studyRepository.save(study)).thenReturn(study);

        studyService.createdNewStudy(1L, study);
        assertNotNull(study.getName());
        assertEquals(member.getId(), study.getOwnerId());
    }
}