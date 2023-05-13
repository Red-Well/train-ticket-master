package com.hhb.trainbookingsystem.dao;

import com.hhb.trainbookingsystem.entity.StationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * 内容：站点信息
 */
@Repository
public interface StationsRepository extends JpaRepository<StationsEntity,Integer> {
    List<StationsEntity> findStationsEntitiesByTripId(int id);
    @Transactional
    @Modifying
    @Query(value="update stations set  arrive_time =?1 where id =?2",nativeQuery=true)
    void updateRoutelineEntityById(Timestamp arrive_time , int id);
    @Transactional
    void deleteStationsEntityById(int id);

    @Transactional
//    @Query(value="select arrive_time from stations where station_name=?1 and trip_id=?2",nativeQuery=true)
    @Query(value="select arrive_time from stations where station_name like concat('%', ?1, '%') and trip_id=?2",nativeQuery=true)
    Timestamp getStationTimeByTripIdAndStation(String start,int tripId);
    StationsEntity findStationsEntityById(int id);


}
