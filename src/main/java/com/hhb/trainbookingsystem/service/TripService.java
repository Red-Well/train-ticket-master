/**
 内容：车次的Service层
 **/
package com.hhb.trainbookingsystem.service;

import com.hhb.trainbookingsystem.entity.TripEntity;

import java.sql.Timestamp;
import java.util.List;

public interface TripService {

    void save(TripEntity t);
    List<TripEntity> findAll();
    List<TripEntity> findAllNew();
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
    List<TripEntity> findTripEntitiesByTrainNumberContaining(String number);
    //根据ID删除
    void deleteTripEntityById(int id);
    //更改车次信息
    void updateTripEntityById(String train_number,Timestamp departureTime, Byte status, int id);
    void updateRemainSeatByTripId(String afterRemain,int tripId);
    String findRemainById(int id);
    Byte findStatusById(int id);
}
