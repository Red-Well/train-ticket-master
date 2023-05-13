/**
 内容：列车的Service层
 **/
package com.hhb.trainbookingsystem.service;

import com.hhb.trainbookingsystem.entity.TrainEntity;

import java.util.List;

public interface TrainService {
    //所有列车信息
    List<TrainEntity> findAll();
    List<TrainEntity> findAllNew();
    //存储列车信息
    TrainEntity save(TrainEntity t);
    //根据ID找列车
    TrainEntity findTrainEntityById(int id);
    //根据车型找列车
    List<TrainEntity> findTrainEntitiesByTrainTypeContaining(String type);
    //根据ID删除列车
    void deleteTrainEntityById(int id);
    //更新列车信息
    TrainEntity updateTrainEntityById(String train_type,String seat_info, int id);
    String findSeatInfoById(int id);
}
