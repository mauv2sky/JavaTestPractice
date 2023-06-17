package com.example.javatestpractice.repository;

import com.example.javatestpractice.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
