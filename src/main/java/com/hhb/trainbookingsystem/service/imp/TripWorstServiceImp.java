package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.dao.TripWorstRepository;
import com.hhb.trainbookingsystem.entity.logResult.TripWorstEntity;
import com.hhb.trainbookingsystem.service.TripWorstService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripWorstServiceImp implements TripWorstService {
    public final TripWorstRepository tripWorstRepository;

    public TripWorstServiceImp(TripWorstRepository tripWorstRepository) {
        this.tripWorstRepository = tripWorstRepository;
    }

    @Override
    public List<TripWorstEntity> findAll() {
        return tripWorstRepository.findAllNew();
    }
}
