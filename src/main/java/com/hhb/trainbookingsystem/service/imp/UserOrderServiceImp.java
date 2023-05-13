/**
 内容：用户订单的Service层实现
 **/
package com.hhb.trainbookingsystem.service.imp;
import com.hhb.trainbookingsystem.dao.UserOrderRepository;
import com.hhb.trainbookingsystem.entity.UserOrderEntity;
import com.hhb.trainbookingsystem.enums.OperationType;
import com.hhb.trainbookingsystem.enums.OperationUnit;
import com.hhb.trainbookingsystem.annotation.Operation;

import com.hhb.trainbookingsystem.entity.custom.EntityUtils;
import com.hhb.trainbookingsystem.entity.custom.Userorder_search;
import com.hhb.trainbookingsystem.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserOrderServiceImp implements UserOrderService {
    @Autowired
    UserOrderRepository userOrderRepository;


    private final UserOrderRepository uor;

 //   @Operation(value = "查看订单",level = 2,operationUnit = OperationUnit.USER,operationType = OperationType.SELETE)
    @Override
    public List<UserOrderEntity> orderstate_get(int id, String state) {
        return userOrderRepository.orderstate_get(id,state);
    }

    public UserOrderServiceImp(UserOrderRepository uor) {
        this.uor = uor;
    }

    @Override
    public void save(UserOrderEntity u) {
        uor.save(u);
    }

    @Override
    @Cacheable(value = "Order",key = "")
    public List<UserOrderEntity> findAll() {
        return uor.findAll();
    }

    @Override
    public List<UserOrderEntity> findAllNew() {
        return uor.findAll();
    }


    @Override
    @Cacheable(value = "Order",key = "#id")
    public UserOrderEntity findUserOrderEntityById(int id) {
        return uor.findUserOrderEntityById(id);
    }

    @Override
    public List<UserOrderEntity> findUserOrderEntitiesByTripNumber(String trip_number) {
        return uor.findUserOrderEntitiesByTripNumber(trip_number);
    }

    @Operation(value = "删除订单",level = 5,operationType = OperationType.DELETE,operationUnit = OperationUnit.USER)
    @Override
    public void deleteUserOrderEntityById(int id) {
        uor.deleteUserOrderEntityById(id);
    }

    @Override
    @CachePut(value = "Order",key = "#result.id")
    public UserOrderEntity updateUserOrderEntityById1(String condition, int id) {
        uor.updateUserOrderEntityById(condition, id);
        return uor.findUserOrderEntityById(id);
    }


    public List<Userorder_search> orderinfo(int id) {
        List<Object[]> order=userOrderRepository.findorderbyid(id);
        List<Userorder_search> orderinfo= EntityUtils.castEntity(order,Userorder_search.class,new Userorder_search());
        return orderinfo;
    }

    @Override
    public void updateUserOrderEntityById(String condition, int id) {

        userOrderRepository.updateUserOrderEntityById(condition, id);

    }
    @Operation(value = "生成订单",level = 5,operationUnit = OperationUnit.USER,operationType = OperationType.INSERT)
    @Override
    public void addOrderLog(String start, String end, String tripName) {
    }

    @Operation(value = "改签订单",level = 5,operationUnit = OperationUnit.USER,operationType = OperationType.INSERT)
    @Override
    public void addUpdateLog(String start, String end, String tripName) {
    }
    @Operation(value = "退票",level = 5,operationUnit = OperationUnit.USER,operationType = OperationType.INSERT)
    @Override
    public void addReturnLog(String start,String end, String tripName) {
    }

   // @Operation(value = "查看订单",level = 3,operationUnit = OperationUnit.USER,operationType = OperationType.SELETE)
    @Override
    public List<Userorder_search> order_paystate(int id, String state) {
       List<Object[]> users= userOrderRepository.notpayorder(id,state);
       List<Userorder_search> orderlist=EntityUtils.castEntity(users,Userorder_search.class,new Userorder_search());
       return orderlist;
    }




}
