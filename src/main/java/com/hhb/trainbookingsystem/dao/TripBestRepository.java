package com.hhb.trainbookingsystem.dao;

import com.hhb.trainbookingsystem.entity.logResult.TripBestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TripBestRepository extends JpaRepository<TripBestEntity,Integer> {

    @Query(value="select * from tripBest order by times DESC",nativeQuery=true)
    List<TripBestEntity> findAllNew();
}
