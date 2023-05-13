/**
 内容：用户订单的DAO层
 **/
package com.hhb.trainbookingsystem.dao;


import com.hhb.trainbookingsystem.entity.UserOrderEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrderEntity,Integer> {
    //通过ID找订单
    UserOrderEntity findUserOrderEntityById(int id);
    //通过车次编号找订单
    List<UserOrderEntity> findUserOrderEntitiesByTripNumber(String trip_number);
    //通过车次ID找订单

    //通过乘车人ID找订单price
    @Transactional
    @Modifying
    @Query(value="select * from user_order",nativeQuery=true)
    List<UserOrderEntity> findAll();

    //通过ID删除订单
    @Transactional
    void deleteUserOrderEntityById(int id);
    //修改订单信息
    @Transactional
    @Modifying
    @Query(value="update user_order set  user_order_condition =?1 where id =?2",nativeQuery=true)
    UserOrderEntity updateUserOrderEntityById1(String condition, int id);

    @Transactional
    @Modifying
    @Query(value="update UserOrderEntity u set u.userOrderCondition =?1 where u.id=?2")
    void updateUserOrderEntityById(String condition, int id);



    //根据userid查找未支付订单信息
    @Query(value = "select a.id,b.trainNumber,a.nameList,a.seatList,a.price,b.startStation,b.endStation,b.departureTime,a.pricelist from " +
            "UserOrderEntity a,TripEntity b where a.tripId=b.id  and a.ordineryUserId=?1 and a.userOrderCondition=?2")
    List<Object[]> notpayorder(int id, String state);

    @Query(value = "select a.id,b.trainNumber,a.nameList,a.seatList,a.price,b.startStation,b.endStation,b.departureTime,a.pricelist from " +
            "UserOrderEntity a,TripEntity b where a.tripId=b.id and a.id=?1")
    List<Object[]> findorderbyid(int id);

    @Query("select o from UserOrderEntity o where o.ordineryUserId=?1 and  o.userOrderCondition=?2")
    List<UserOrderEntity>  orderstate_get(int id,String state);
}
