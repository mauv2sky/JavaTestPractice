package com.example.javatestpractice.junit;

import com.example.javatestpractice.domain.Study;
import com.example.javatestpractice.domain.StudyStatus;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * 테스트 코드에 이름을 명시하는 두가지 방법
 * 1. @DisplayNameGeneration : 클래스 내의 모든 메소드에 대해 적용
 * 2. @DisplayName : 각 메소드에 적용
 * */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study(10);

        // assert를 따로 작성할 수 있지만 통과하지 못한 다음의 테스트는 실행되지 않는 단점
        // assertAll을 사용하면 묶어진 테스트의 결과를 모두 확인할 수 있다.
        assertAll(
                () -> assertNotNull(study),
                // assertEquals params : 기대값, 실제값, 오류 시 처리
                // 오류 메시지를 람다식으로 작성하면, 오류 시에만 동작해서 연산을 효율적으로 하게 됨
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, () -> "스터디 참석인원은 0보다 커야 한다.")
        );

        // 특정 조건에서 특정 예외를 발생시키는지 테스트
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-1));
        assertEquals("limit는 0보다 커야한다.", exception.getMessage());

        // 일정 시간 내에 실행이 끝나는지 테스트
        assertTimeout(Duration.ofSeconds(10), () -> {
            new Study(10);
            Thread.sleep(300);
        });
    }

    @Test
    @DisplayName("조건에 따라 테스트 만들기")
    void create_new_study_assume() {
        // 방법1. 조건이 성립하면 다음 테스트를 진행
        String test_env = System.getenv("TEST_ENV");
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        // 방법2. 조건에 따라 테스트 구분
        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            System.out.println("local");
            Study actual = new Study(10);
        });

        assumingThat("DEV".equalsIgnoreCase(test_env), () -> {
            System.out.println("dev");
            Study actual = new Study(100);
        });

    }
}
