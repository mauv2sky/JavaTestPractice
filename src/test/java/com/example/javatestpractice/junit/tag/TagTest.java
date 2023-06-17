package com.example.javatestpractice.junit.tag;

import com.example.javatestpractice.domain.Study;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * JUnit에서는 테스트를 태그로 분류하여 실행할 수 있게 한다.
 * 두가지 방법에 대해 다룬다.
 * */
public class TagTest {

    // 방법1. @Tag 어노테이션을 사용해서 분류
    // 오타 가능성, 붙는 태그수가 비교적 많음
    @Test
    @Tag("fast")
    @DisplayName("fast - @Tag")
    void create_new_study_fast1() {
        Study actual = new Study(100);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    // 방법2. 커스텀 어노테이션을 만들어 사용
    // 기존의 @Test, @Tag 어노테이션을 단축. 오타 위험 감소
    @FastTest
    @DisplayName("스터디 만들기 fast")
    void create_new_study_fast2() {
        Study actual = new Study(100);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @SlowTest
    @DisplayName("스터디 만들기 slow")
    void create_new_study_slow() {
        Study actual = new Study(100);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }
}
