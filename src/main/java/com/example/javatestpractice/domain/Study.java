package com.example.javatestpractice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Study {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;
    private LocalDateTime openedDateTime;
    private Long ownerId;

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

    public void open() {
        this.openedDateTime = LocalDateTime.now();
        this.status = StudyStatus.OPENED;
    }
}
