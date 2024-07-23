package com.MMS.Member_Monitoring_System.services;

import com.MMS.Member_Monitoring_System.models.Member;
import com.MMS.Member_Monitoring_System.models.MemberFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberFeeRepository extends JpaRepository<MemberFee, Long> {
    List<MemberFee> findByMember(Member member);
}
