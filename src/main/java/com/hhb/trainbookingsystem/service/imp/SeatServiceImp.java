package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.dao.SeatRepository;
import com.hhb.trainbookingsystem.entity.SeatEntity;
import com.hhb.trainbookingsystem.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImp implements SeatService {
    @Autowired
    public SeatRepository seatRepository;
    @Override
    public void save(SeatEntity s) {
        seatRepository.save(s);
    }

    @Override
    public void deleteSeatEntitiesByTripId(int id) {
        seatRepository.deleteSeatEntitiesByTripId(id);
    }

    @Override
    public String getSeatByStartEndTripId(String first, String next, int trip_id) {
        return seatRepository.getSeatByStartEndTripId(first, next, trip_id);
    }

    @Override
    public void updateSeatInfoByTripId(String s, String startFirst, String endNext, int tripId) {
        seatRepository.updateSeatInfoByTripId(s, startFirst, endNext, tripId);
    }
}
