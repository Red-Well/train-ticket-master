/**
 内容：用户订单的Service层
 **/
package com.hhb.trainbookingsystem.service;
import com.hhb.trainbookingsystem.entity.UserOrderEntity;
import com.hhb.trainbookingsystem.entity.custom.Userorder_search;

import java.util.List;

public interface UserOrderService {
    void save(UserOrderEntity u);
    List<UserOrderEntity> findAll();
    List<UserOrderEntity> findAllNew();
    //通过ID找订单
    UserOrderEntity findUserOrderEntityById(int id);
    //通过车次编号找订单
    List<UserOrderEntity> findUserOrderEntitiesByTripNumber(String trip_number);
    //通过车次ID找订单

    //通过乘车人ID找订单price

    //通过ID删除订单
    void deleteUserOrderEntityById(int id);
    //修改订单信息

    UserOrderEntity updateUserOrderEntityById1(String condition, int id);

    void updateUserOrderEntityById(String condition, int id);

    //写入生成订单日志
    void addOrderLog(String start,String end, String tripName);
    //写入退票日志
    void addReturnLog(String start,String end, String tripName);
    //写入改签
    void addUpdateLog(String start, String end, String tripName);
    //根据状态查找订单
    List<Userorder_search> order_paystate(int id, String state);

    //根据id查找订单及列车信息
    List<Userorder_search> orderinfo(int id);

    //根据状态和id查找订单
    List<UserOrderEntity>  orderstate_get(int id,String state);
}
