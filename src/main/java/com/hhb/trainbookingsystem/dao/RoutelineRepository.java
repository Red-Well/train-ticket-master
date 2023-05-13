package com.hhb.trainbookingsystem.dao;

import com.hhb.trainbookingsystem.entity.RoutelineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


@Repository
public interface RoutelineRepository extends JpaRepository<RoutelineEntity,Integer> {
    //根据车次ID找到车次
     RoutelineEntity findRoutelineEntityByTripId(int id);
    RoutelineEntity findRoutelineEntityById(int id);
    @Transactional
    @Modifying
    @Query(value="update routeline set  route_line =?1 where id =?2",nativeQuery=true)
    void updateRoutelineEntityById(String route_line , int id);

    @Transactional
    @Modifying
    @Query(value = "select * from routeline where route_line like concat('%', ?1, '%', ?2, '%') ",nativeQuery=true)
    List<RoutelineEntity> findRouteEntitiesByStations(String start,String end);

    @Query(value = "select route_line from routeline where trip_id = ?1 ",nativeQuery=true)
    String getRouteLineByTripId(int id);

}
