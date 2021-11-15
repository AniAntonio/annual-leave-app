package com.lhind.repository;

import com.lhind.entities.AnnualLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnnualLeaveRepository extends JpaRepository<AnnualLeave, Integer> {
    @Query("select al from #{#entityName} al where al.user.id = ?1 ")
    Optional<AnnualLeave> findByUserId(Integer userId);
}
