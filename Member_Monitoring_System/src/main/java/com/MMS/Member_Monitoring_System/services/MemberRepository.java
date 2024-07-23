package com.MMS.Member_Monitoring_System.services;

import com.MMS.Member_Monitoring_System.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
