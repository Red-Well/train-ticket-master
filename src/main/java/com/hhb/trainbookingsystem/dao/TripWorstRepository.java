package com.hhb.trainbookingsystem.dao;
import com.hhb.trainbookingsystem.entity.logResult.TripWorstEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TripWorstRepository extends JpaRepository<TripWorstEntity,Integer> {
    @Query(value="select * from tripWorst order by times DESC",nativeQuery=true)
    List<TripWorstEntity> findAllNew();
}
