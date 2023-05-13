package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.dao.FareRespository;
import com.hhb.trainbookingsystem.dao.TripRepository;
import com.hhb.trainbookingsystem.entity.FareEntity;
import com.hhb.trainbookingsystem.entity.TripEntity;
import com.hhb.trainbookingsystem.service.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"FareCache"})
public class FareServiceImp implements FareService {
    public final FareRespository fareRespository;
    @Autowired
    private final TripRepository tpr;
    public FareServiceImp(FareRespository fareRespository, TripRepository tpr) {
        this.fareRespository = fareRespository;
        this.tpr = tpr;
    }

    @Override
    @CacheEvict(value="Fare",key="#f.tripId")
    public FareEntity save(FareEntity f) {
        fareRespository.save(f);
        return f;
    }

    @Override
    public List<FareEntity> findAll() {
        return  fareRespository.findAll();
    }

    @Override
    @Cacheable(value = "Fare",key = "#id")
    public List<FareEntity> findFareEntitiesByTripId(int id) {
        return fareRespository.findFareEntitiesByTripId(id);
    }

    @Override
    @CachePut(value="Fare",key="#result[0].tripId")
    public List<FareEntity> updateFareEntityById(BigDecimal price, int id) {
        fareRespository.updateFareEntityById(price,id);
        int tripId = fareRespository.findFareEntityById(id).getTripId();
        return  fareRespository.findFareEntitiesByTripId(tripId);
    }

    @Override
    @CacheEvict(value="Fare",key="#result.id")
    public TripEntity deleteFareEntityById(int id) {
        TripEntity tripEntity = tpr.findTripEntityById(fareRespository.findFareEntityById(id).getTripId());
        fareRespository.deleteFareEntityById(id);
        return  tripEntity;
    }

    @Override
    public BigDecimal getFareByStationsAndTripId(String start, String end, String type, int tripId) {
        return fareRespository.getFareByStationsAndTripId(start, end, type, tripId);
    }
}
