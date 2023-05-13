package com.hhb.trainbookingsystem.dao;

import com.hhb.trainbookingsystem.entity.LoginfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRespository extends JpaRepository<LoginfoEntity,Integer> {

}
