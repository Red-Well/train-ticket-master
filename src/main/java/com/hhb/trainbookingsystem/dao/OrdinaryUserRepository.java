package com.hhb.trainbookingsystem.dao;

import com.hhb.trainbookingsystem.entity.OrdinaryUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 内容：普通用户管理
 * 内容：增加了用户的删除修改查找等功能
 */

@Repository
public interface OrdinaryUserRepository extends JpaRepository<OrdinaryUserEntity,Integer>, JpaSpecificationExecutor<OrdinaryUserEntity> {

    @Query("select t from OrdinaryUserEntity t where t.name=?1 and t.password=?2")
    public OrdinaryUserEntity TrygetUser(String name,String password);

    @Query("select t from OrdinaryUserEntity t where t.name=?1")
    public OrdinaryUserEntity finduserbyname(String name);
    //根据ID查找用户
    OrdinaryUserEntity findOrdinaryUserEntityById(int id);
    //根据ID删除用户
    @Transactional
    void deleteOrdinaryUserEntityById(int id);
    //根据姓名查找用户
    List<OrdinaryUserEntity> findOrdinaryUserEntitiesByNameContaining(String s);
    //根据ID编辑用户
    @Transactional
    @Modifying
    @Query(value="update ordinary_user set name = ?1,person_id=?2,isstudent=?3,credit=?4  where id =?5",
            nativeQuery=true)
    void updateUserById(String name,String person_id,Byte is_student,Byte credit ,int id);

    //根据name修改用户
    @Transactional
    @Modifying
    @Query("update OrdinaryUserEntity u set u.phonenum=?1 where u.name=?2 ")
    void updatebynames(String phonenum,String names);

}
