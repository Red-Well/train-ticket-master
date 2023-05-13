package com.hhb.trainbookingsystem.dao;

import com.hhb.trainbookingsystem.entity.logResult.LogResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogResultRepository extends JpaRepository<LogResultEntity,Integer> {
    LogResultEntity findLogResultEntityByUsername(String username);
}
