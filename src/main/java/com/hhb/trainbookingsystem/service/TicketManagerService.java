/**
 内容：票务管理员的Service层
 **/
package com.hhb.trainbookingsystem.service;

import com.hhb.trainbookingsystem.entity.TicketManagerEntity;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;


public interface TicketManagerService {
    //找到所有票务端用户
    List<TicketManagerEntity> findALL();
    //储存票务端用户
   TicketManagerEntity save(TicketManagerEntity t);
    //通过id 找票务端用户
    TicketManagerEntity findTicketManagerEntityById(int id);
    //通过名字找票务端用户
    TicketManagerEntity findTicketManagerEntityByNameContains(String name);
    List<TicketManagerEntity> findTicketManagerEntitiesByNameContainingOrStaffIdContaining(String name);
    TicketManagerEntity findTicketManagerEntityByNameAndPassword(String name,String pw);
    //通过工号找票务端用户
    TicketManagerEntity findTicketManagerEntityByStaffIdNotContains(String staff_id);
    //通过ID删除票务端用户
    @Transactional
    void deleteTicketManagerEntityById(int id);
    //通过ID修改票务端用户信息
    TicketManagerEntity updateTicketManagerById1(String name,String password,String staffId,int id);
    TicketManagerEntity updateTicketManagerById2(String name,String password,int id);

    //分页显示售票盾用户
    Page<TicketManagerEntity> findTicketManagers(int page, int pageSize);
}
