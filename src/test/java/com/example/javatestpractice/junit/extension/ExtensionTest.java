package com.example.javatestpractice.junit.extension;

import com.example.javatestpractice.domain.Study;
import com.example.javatestpractice.junit.tag.FastTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(FindSlowTestExtension.class) // 방법1. 클래스 명시
public class ExtensionTest {

    // 방법2. 필드 명시
    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension =
            new FindSlowTestExtension(1000L);

    @FastTest
    @DisplayName("스터디 만들기 fast")
    void create_new_study_fast2() {
        Study actual = new Study(100);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @Test
    @DisplayName("스터디 만들기 slow")
    void create_new_study_slow() throws InterruptedException {
        Thread.sleep(1005L);
        Study actual = new Study(100);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }
}
