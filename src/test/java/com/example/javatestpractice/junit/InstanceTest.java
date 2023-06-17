package com.example.javatestpractice.junit;

import org.junit.jupiter.api.*;


/**
 * JUnit의 전략
 * 기본적으로 테스트 메서드마다 테스트의 인스턴스를 별도로 만듬
 * -> 테스트 간의 의존성을 낮추기 위해
 * -> 테스트 실행 순서가 코드 정의된 순서라고 생각하는 것은 지양
 *
 * @TestInstance : 인스턴스 범위 설정
 * @TestMethodOrder : 메서드 순서 정의
 * */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // 한 클래스에 하나의 인스턴스
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InstanceTest {

    int value = 1;

    @Test
    @Order(2)
    @DisplayName("스터디 만들기 1")
    void create_new_study1() {
        System.out.println(this);  // 3c9754d8
        System.out.println("create 1 : " + value++);  // 1
    }

    @Test
    @Order(1)
    @DisplayName("스터디 만들기 2")
    void create_new_study2() {
        System.out.println(this);  // 5f9b2141
        System.out.println("create 2 : " + value++);  // 1
    }


    // 메소드 별 인스턴스를 가졌을 때는 beforeAll, afterAll 메서드를 static으로 정의해야 했음
    // 클래스 인스턴스를 가질 때는 static을 명시하지 않아도 됨
    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }
}
