package com.example.javatestpractice.junit.tag;

import com.example.javatestpractice.domain.Study;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParamTest {

    // 방법1. 정해진 데이터타입의 여러 값을 인자로 받아 사용
    @DisplayName("parameter")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"나는", "자바", "테스트", "잘한다!"})
    void parameterizedTest(String message) {
        System.out.println(message);
    }

    // 방법2. 정해진 데이터타입의 여러 값을 특정 객체로 치환해 사용
    // SimpleArgumentConverter는 하나의 타입에만 사용할 수 있다.
    @DisplayName("parameter")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest2(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    // 방법3. 미정된 데이터타입의 여러 값을 특정 객체로 치환해 사용
    @DisplayName("parameter")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, 자바스터디", "20, 스프링"})
    void parameterizedTest3(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }
}
