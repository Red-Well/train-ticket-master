/**
内容：票务管理员的DAO层
**/
package com.hhb.trainbookingsystem.dao;
import com.hhb.trainbookingsystem.entity.TicketManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TicketManagerRepository extends JpaRepository<TicketManagerEntity,Integer>, JpaSpecificationExecutor<TicketManagerEntity> {

    //通过id 找票务端用户
    TicketManagerEntity findTicketManagerEntityById(int id);
    //通过名字找票务端用户
    TicketManagerEntity findTicketManagerEntityByNameContains(String name);
    //通过名字密码寻找用户
    TicketManagerEntity findTicketManagerEntityByNameAndPassword(String name,String pw);
    List<TicketManagerEntity> findTicketManagerEntitiesByNameContaining(String name);
    //通过工号找票务端用户
    TicketManagerEntity findTicketManagerEntityByStaffIdNotContains(String staff_id);
    //通过ID删除票务端用户
    @Transactional
    void deleteTicketManagerEntityById(int id);
    //通过ID修改票务端用户信息
    @Transactional
    @Modifying
    @Query(value="update ticket_manager set name = ?1,password=?2,staff_id=?3 where id =?4",nativeQuery=true)
    void updateTicketManagerById1(String name,String password,String staff_id,int id);
    @Transactional
    @Modifying
    @Query(value="update ticket_manager set name = ?1,password=?2 where id =?3",nativeQuery=true)
    void updateTicketManagerById2(String name,String password,int id);
}
