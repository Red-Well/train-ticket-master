package com.hhb.trainbookingsystem.dao;

import com.hhb.trainbookingsystem.entity.AdvertisementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
import javax.transaction.Transactional;

/**

 * 内容：广告管理

 * 内容：更新广告管理
 */
@Repository
public interface AdvertisementRespository extends JpaRepository<AdvertisementEntity, Integer>, JpaSpecificationExecutor<AdvertisementEntity> {
    //依靠链接检索
    List<AdvertisementEntity> findAdvertisementEntitiesByLinkContaining(String s);
    //依靠ID检索
    AdvertisementEntity findAdvertisementEntityById(int id);
    //依靠ID删除
    @Transactional
    void deleteAdvertisementEntityById(int id);
    @Transactional
    @Modifying
    @Query(value="update advertisement set link = ?1, photo=?2  where id =?3",nativeQuery=true)
    void updateAdById(String link,String photo, int id);
}
