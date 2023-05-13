/*
* 开发内容：广告类的service
* */
package com.hhb.trainbookingsystem.service;

import com.hhb.trainbookingsystem.entity.AdvertisementEntity;

import java.util.List;

public interface AdvertisementService {
    AdvertisementEntity save(AdvertisementEntity a);
    List<AdvertisementEntity> findAll();
    //依靠链接检索
    List<AdvertisementEntity> findAdvertisementEntitiesByLinkContaining(String s);
    //依靠ID检索
    AdvertisementEntity findAdvertisementEntityById(int id);
    //依靠ID删除
    void deleteAdvertisementEntityById(int id);
    //更新广告表
    AdvertisementEntity updateAdById(String link,String photo, int id);
}
