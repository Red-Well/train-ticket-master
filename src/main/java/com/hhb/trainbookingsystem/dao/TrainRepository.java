/**
 内容：列车的DAO层
 **/
package com.hhb.trainbookingsystem.dao;
import com.hhb.trainbookingsystem.entity.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<TrainEntity,Integer> {
    //根据ID找列车
    TrainEntity findTrainEntityById(int id);

    //根据车型找列车
    List<TrainEntity> findTrainEntitiesByTrainTypeContaining(String type);
    //根据ID删除列车
    @Transactional
    void deleteTrainEntityById(int id);
    //更新列车信息
    @Transactional
    @Modifying
    @Query(value="update train set train_type = ?1, seat_info=?2 where id =?3",nativeQuery=true)
    void updateTrainEntityById(String train_type,String seat_info, int id);
    @Query(value="select seat_info from train where id =?1",nativeQuery=true)
    String findSeatInfoById(int id);
}
