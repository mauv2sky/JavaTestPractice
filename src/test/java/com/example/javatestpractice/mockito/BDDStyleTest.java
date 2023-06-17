package com.example.javatestpractice.mockito;

import com.example.javatestpractice.domain.Member;
import com.example.javatestpractice.domain.Study;
import com.example.javatestpractice.domain.StudyStatus;
import com.example.javatestpractice.repository.StudyRepository;
import com.example.javatestpractice.service.MemberService;
import com.example.javatestpractice.service.StudyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

/**
 * Behavior-Driven Development
 * */
@ExtendWith(MockitoExtension.class)
public class BDDStyleTest {

    @Mock
    MemberService memberService;
    @Mock
    StudyRepository studyRepository;

    @Test
    void createNewStudyService() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("ahyeon@mail.com");

        Study study = new Study(10, "테스트");

        BDDMockito.given(memberService.findById(1L)).willReturn(Optional.of(member)); // mockito.when -> given
        BDDMockito.given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.createdNewStudy(1L, study);

        // Then
        BDDMockito.then(memberService).should(Mockito.times(1)).notifyStudy(study); // mockito.verify -> then
        BDDMockito.then(memberService).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    void openStudy() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "더 자바, 테스트");

        // TODO: studyRepository Mock 객체의 save 메소드 호출 시 study를 리턴
        BDDMockito.given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.openStudy(study);

        // Then
        // TODO: study의 status가 OPENED로 변경됐는지 확인
        assertEquals(StudyStatus.OPENED, study.getStatus());
        // TODO: study의 openedDateTime이 null이 아닌지 확인
        assertNotNull(study.getOpenedDateTime());
        // TODO: memberService의 notify(study)가 호출됐는지 확인
        BDDMockito.then(memberService).should().notifyStudy(study);
    }
}