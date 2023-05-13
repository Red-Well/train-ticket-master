package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.dao.StationsRepository;
import com.hhb.trainbookingsystem.dao.TripRepository;
import com.hhb.trainbookingsystem.entity.StationsEntity;
import com.hhb.trainbookingsystem.entity.TripEntity;
import com.hhb.trainbookingsystem.service.StationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class StationsServiceImp implements StationsService {
    @Autowired
    private final TripRepository tpr;
    public final StationsRepository stationsRepository;


    public StationsServiceImp(TripRepository tpr, StationsRepository stationsRepository) {
        this.tpr = tpr;
        this.stationsRepository = stationsRepository;
    }

    @Override
    @CacheEvict(value="Station",key="#s.tripId")
    public StationsEntity save(StationsEntity s) {
        stationsRepository.save(s);
        return s;
    }
    @Override
    public List<StationsEntity> newFindStationsEntitiesByTripId(int id) {
        return stationsRepository.findStationsEntitiesByTripId(id);
    }

    @Override
   // @Cacheable(value="Station",key = "#id")
    public List<StationsEntity> findStationsEntitiesByTripId(int id) {
        return stationsRepository.findStationsEntitiesByTripId(id);
    }

    @Override
   // @CachePut(value="Station",key="#result[0].tripId")
    public List<StationsEntity> updateStationEntityById(Timestamp arrive_time, int id) {
        stationsRepository.updateRoutelineEntityById(arrive_time, id);
        int tripId = stationsRepository.findStationsEntityById(id).getTripId();
        return stationsRepository.findStationsEntitiesByTripId(tripId);
    }

    @Override
   // @CacheEvict(value="Station",key="#result.id")
    public TripEntity deleteStationsEntityById(int id) {
        TripEntity T = tpr.findTripEntityById(stationsRepository.findStationsEntityById(id).getTripId());
        stationsRepository.deleteStationsEntityById(id);
        return  T;
    }

    @Override
  //  @Cacheable(value="Station1",key = "#start+'-'+#id")
    public Timestamp getStationTimeByTripIdAndStation(String start, int tripId) {
        System.out.println(stationsRepository.getStationTimeByTripIdAndStation(start, tripId));
        return stationsRepository.getStationTimeByTripIdAndStation(start, tripId);
    }
}
