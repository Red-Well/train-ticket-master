package com.hhb.trainbookingsystem.service;

import com.hhb.trainbookingsystem.entity.StationsEntity;
import com.hhb.trainbookingsystem.entity.TripEntity;

import java.sql.Timestamp;
import java.util.List;

public interface StationsService {
    StationsEntity save(StationsEntity s);
    List<StationsEntity> newFindStationsEntitiesByTripId(int id);
    List<StationsEntity> findStationsEntitiesByTripId(int id);
    List<StationsEntity> updateStationEntityById(Timestamp arrive_time , int id);
    TripEntity deleteStationsEntityById(int id);
    Timestamp getStationTimeByTripIdAndStation(String start,int tripId);
}
