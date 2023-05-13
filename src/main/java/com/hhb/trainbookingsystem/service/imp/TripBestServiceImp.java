package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.dao.TripBestRepository;
import com.hhb.trainbookingsystem.entity.logResult.TripBestEntity;
import com.hhb.trainbookingsystem.service.TripBestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripBestServiceImp implements TripBestService {
    private final TripBestRepository tripBestRepository;

    public TripBestServiceImp(TripBestRepository tripBestRepository) {
        this.tripBestRepository = tripBestRepository;
    }


    @Override
    public List<TripBestEntity> findAll() {
        return tripBestRepository.findAllNew();
    }

}
