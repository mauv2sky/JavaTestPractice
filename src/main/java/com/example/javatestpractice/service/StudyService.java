package com.example.javatestpractice.service;

import com.example.javatestpractice.domain.Member;
import com.example.javatestpractice.domain.Study;
import com.example.javatestpractice.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final MemberService memberService;
    private final StudyRepository repository;

    public Study createdNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        if(member.isPresent()) {
            study.setOwnerId(memberId);
        } else {
            throw new IllegalArgumentException("Member doesn't exist for id : " + memberId);
        }
        Study newStudy = repository.save(study);
        memberService.notifyStudy(newStudy);
        return newStudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = repository.save(study);
        memberService.notifyStudy(openedStudy);
        return openedStudy;
    }
}
