package com.example.javatestpractice.service;

import com.example.javatestpractice.domain.Member;
import com.example.javatestpractice.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);
    void validate(Long memberId);
    void notifyStudy(Study newStudy);
    void notifyMember(Member member);
}
