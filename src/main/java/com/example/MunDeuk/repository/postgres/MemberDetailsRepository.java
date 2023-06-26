package com.example.MunDeuk.repository.postgres;

import com.example.MunDeuk.models.postgres.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDetailsRepository extends JpaRepository<MemberDetails,Long> {

}
