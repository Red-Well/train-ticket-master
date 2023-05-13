/**
 内容：车次的Service层实现
 **/
package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.dao.TripRepository;
import com.hhb.trainbookingsystem.entity.TripEntity;
import com.hhb.trainbookingsystem.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private final TripRepository tpr;

    public TripServiceImpl(TripRepository tpr) {
        this.tpr = tpr;
    }

    @Override
    public void save(TripEntity t) {
        tpr.save(t);
    }

    @Override
    @Cacheable(value = "Trip" ,key = "")
    public List<TripEntity> findAll() {
        return tpr.findAll();
    }

    @Override
    public List<TripEntity> findAllNew() {
        return tpr.findAll();
    }
    @Override
    @Cacheable(value = "Trip" ,key = "#number")
    public List<TripEntity> findTripEntitiesByTrainNumberContaining(String number) {
        return tpr.findTripEntitiesByTrainNumberContaining(number);
    }
    @Override

    public void updateRemainSeatByTripId(String afterRemain, int tripId) {
        tpr.updateRemainSeatByTripId(afterRemain, tripId);
    }
    @Override
    public TripEntity findTripEntityByTrainNumber(String num) {
        return tpr.findTripEntityByTrainNumber(num);
    }
    @Override
    public TripEntity findTripEntityById(int id) {
        return tpr.findTripEntityById(id);
    }
    @Override
    public TripEntity findTripEntityByStartStation(String start) {
        return tpr.findTripEntityByStartStation(start);
    }
    @Override
    public TripEntity findTripEntityByEndStation(String end) {
        return tpr.findTripEntityByEndStation(end);
    }
    @Override
    public List<TripEntity> findTripEntitiesByDepartureTimeIsStartingWith(Timestamp departureTime) {
        return tpr.findTripEntitiesByDepartureTimeIsStartingWith(departureTime);
    }
    @Override
    @Cacheable(value = "Trip" ,key = "#status")
    public List<TripEntity> findTripEntitiesByTripStatus(Byte status) {
        return tpr.findTripEntitiesByTripStatus(status);
    }



    @Override
    public void deleteTripEntityById(int id) {
        tpr.deleteTripEntityById(id);
    }

    @Override
    public void updateTripEntityById(String train_number, Timestamp departureTime, Byte status, int id) {
        tpr.updateTripEntityById(train_number, departureTime, status, id);
    }



    @Override
    public String findRemainById(int id) {
        return tpr.findRemainById(id);
    }

    @Override
    public Byte findStatusById(int id) {
        return tpr.findStatusById(id);
    }
}
