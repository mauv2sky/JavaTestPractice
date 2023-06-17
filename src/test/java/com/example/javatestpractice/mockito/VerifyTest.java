package com.example.javatestpractice.mockito;

import com.example.javatestpractice.domain.Member;
import com.example.javatestpractice.domain.Study;
import com.example.javatestpractice.repository.StudyRepository;
import com.example.javatestpractice.service.MemberService;
import com.example.javatestpractice.service.StudyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * verity : 검증을 완료하기 위한 최소한의 테스트 조건을 명시한다.
 * */
@ExtendWith(MockitoExtension.class)
public class VerifyTest {

    @Test
    void createNewStudyService(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("ahyeon@mail.com");

        Study study = new Study(10, "테스트");

        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Mockito.when(studyRepository.save(study)).thenReturn(study);

        studyService.createdNewStudy(1L, study);
        assertEquals(member.getId(), study.getOwnerId());

        Mockito.verify(memberService, Mockito.times(1)).notifyStudy(study);
        Mockito.verify(memberService, Mockito.times(0)).notifyMember(member);
        Mockito.verify(memberService, Mockito.never()).validate(Mockito.any());

        // 어떤 순서대로 실행됐는지 확인
        InOrder inOrder = Mockito.inOrder(memberService);
        inOrder.verify(memberService).notifyStudy(study);
        inOrder.verify(memberService).notifyMember(member);
    }
}
