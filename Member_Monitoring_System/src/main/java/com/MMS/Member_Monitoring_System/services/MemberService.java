package com.MMS.Member_Monitoring_System.services;

import com.MMS.Member_Monitoring_System.models.Member;
import com.MMS.Member_Monitoring_System.models.MemberFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepo;

    public List<Member> getAllMembers() {
        return memberRepo.findAll();
    }


    public void saveMember(Member member) {
        memberRepo.save(member);
    }
}
