/**
 内容：列车的Service层实现
 **/
package com.hhb.trainbookingsystem.service.imp;
import com.hhb.trainbookingsystem.entity.TrainEntity;
import com.hhb.trainbookingsystem.dao.TrainRepository;
import com.hhb.trainbookingsystem.service.TrainService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainServiceImp implements TrainService {
    private final TrainRepository tr;

    public TrainServiceImp(TrainRepository tr) {
        this.tr = tr;
    }

    @Override
    @Cacheable(value = "Train" ,key = "")
    public List<TrainEntity> findAll() {
        return tr.findAll();
    }
    @Override
    public List<TrainEntity> findAllNew() {
        return tr.findAll();
    }
    @Override
    public TrainEntity save(TrainEntity t) {
        return  tr.save(t);
    }

    @Override
    @Cacheable(value = "Train" ,key = "#id")
    public TrainEntity findTrainEntityById(int id) {
        return tr.findTrainEntityById(id);
    }

    @Override
    public List<TrainEntity> findTrainEntitiesByTrainTypeContaining(String type) {
        return tr.findTrainEntitiesByTrainTypeContaining(type);
    }

    @Override

    public void deleteTrainEntityById(int id) {
        tr.deleteTrainEntityById(id);
    }

    @Override
    @CachePut(value = "Train" ,key = "#result.id")
    public TrainEntity updateTrainEntityById(String train_type, String seat_info, int id) {
        tr.updateTrainEntityById(train_type, seat_info, id);
        return  tr.findTrainEntityById(id);
    }

    @Override
    //@Cacheable(value = "Train" ,key = "#id")
    public String findSeatInfoById(int id) {
        return tr.findSeatInfoById(id);
    }
}
