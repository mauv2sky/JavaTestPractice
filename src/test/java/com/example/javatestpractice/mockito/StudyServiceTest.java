package com.example.javatestpractice.mockito;

import com.example.javatestpractice.repository.StudyRepository;
import com.example.javatestpractice.service.MemberService;
import com.example.javatestpractice.service.StudyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 객체의 구현체가 없고 인터페이스만 존재할 경우,
 * mockito를 통해 실제 객체를 생성한 것처럼 테스트 할 수 있다.
 * 다음 세가지 방법으로 mock 객체를 생성 가능하다.
 * */

@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {

    // 방법1. @Mock 어노테이션 사용
    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService() {
        // 방법2. mock 객체 직접 생성
        MemberService memberService = Mockito.mock(MemberService.class);
        StudyRepository studyRepository = Mockito.mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberService, studyRepository);
    }

    // 방법3. 메소드 args로 @Mock 객체 작성
    @Test
    void createStudyService1(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {

    }
}
