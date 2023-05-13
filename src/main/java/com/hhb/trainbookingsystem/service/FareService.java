package com.hhb.trainbookingsystem.service;

import com.hhb.trainbookingsystem.entity.FareEntity;
import com.hhb.trainbookingsystem.entity.TripEntity;

import java.math.BigDecimal;
import java.util.*;
public interface FareService {
    FareEntity save(FareEntity f);
    List<FareEntity> findAll();
    List<FareEntity>  findFareEntitiesByTripId(int id);
    List<FareEntity> updateFareEntityById(BigDecimal price , int id);
    TripEntity deleteFareEntityById(int id);
    BigDecimal getFareByStationsAndTripId(String start,String end,String type,int tripId);
}
