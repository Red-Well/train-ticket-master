package com.hhb.trainbookingsystem.dao;

import com.hhb.trainbookingsystem.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * 内容：座位管理
 */
@Repository
public interface SeatRepository extends JpaRepository<SeatEntity,Integer> {
    @Transactional
    void deleteSeatEntitiesByTripId(int id);
    @Transactional
    @Query(value = "select seat_info from seat where first_station = ?1 and next_station =?2 and trip_id=?3",nativeQuery=true)
    String getSeatByStartEndTripId(String first,String next,int trip_id);
    @Transactional
    @Modifying
    @Query(value = "update seat set seat_info = ?1  where first_station = ?2 and next_station =?3 and trip_id=?4",nativeQuery=true)
    void updateSeatInfoByTripId(String s, String  startFirst,String endNext,int tripId);
}
