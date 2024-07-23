package com.MMS.Member_Monitoring_System.services;

import com.MMS.Member_Monitoring_System.models.MemberFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberFeeService {

    @Autowired
    private MemberFeeRepository memberFeeRepo;

    public List<MemberFee> getAllMemberFees() {
        return memberFeeRepo.findAll();
    }

    public void saveMemberFee(MemberFee memberFee) {
        memberFeeRepo.save(memberFee);
    }
}
