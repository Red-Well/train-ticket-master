/**
 内容：车次的DAO层
 **/
package com.hhb.trainbookingsystem.dao;
import com.hhb.trainbookingsystem.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripEntity,Integer> {
    //根据ID搜索
    TripEntity findTripEntityById(int id);
    //根据编号搜索
    TripEntity findTripEntityByTrainNumber(String num);
    //根据起发站搜索
    TripEntity findTripEntityByStartStation(String start);
    //根据终止站搜索
    TripEntity findTripEntityByEndStation(String end);
    //根据起发时间搜索
    List<TripEntity> findTripEntitiesByDepartureTimeIsStartingWith(Timestamp departureTime);
    //根据状态搜索
    List<TripEntity> findTripEntitiesByTripStatus(Byte status);
    //根据编号搜索
    //@Query(value="SELECT * FROM booking.trip where train_number like %?1%",nativeQuery=true)
    List<TripEntity> findTripEntitiesByTrainNumberContaining(String number);
    //根据ID删除
    void deleteTripEntityById(int id);
    //更改车次信息
    @Transactional
    @Modifying
    @Query(value="update trip set train_number = ?1, departure_time=?2, trip_status=?3 where id =?4",nativeQuery=true)
    void updateTripEntityById(String train_number,Timestamp departureTime, Byte status, int id);
    @Transactional
    @Modifying
    @Query(value="update trip set remainseat_info = ?1 where id =?2",nativeQuery=true)
    void updateRemainSeatByTripId(String afterRemain,int tripId);
    @Query(value="select  remainseat_info from trip  where id =?1",nativeQuery=true)
    String findRemainById(int id);
    @Query(value="select  trip_status from trip  where id =?1",nativeQuery=true)
    Byte findStatusById(int id);

}
