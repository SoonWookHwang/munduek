package com.example.MunDeuk.repository.postgres;

import com.example.MunDeuk.models.postgres.Locker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockerRepository extends JpaRepository<Locker,Long> {

}
