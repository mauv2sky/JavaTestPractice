package com.example.javatestpractice.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Study {
    private String name;
    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;

    public Study(int limit) {
        if(limit < 0) {
            throw new IllegalArgumentException("limit는 0보다 커야한다.");
        }
        this.limit = limit;
    }

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }
}
