/*
 * 开发内容：广告类的service实现
 * */
package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.dao.AdvertisementRespository;
import com.hhb.trainbookingsystem.entity.AdvertisementEntity;
import com.hhb.trainbookingsystem.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementServiceImp implements AdvertisementService {
    @Autowired
    private AdvertisementRespository advertisementRespository;

    @Override
    public AdvertisementEntity save(AdvertisementEntity a) {
        return advertisementRespository.save(a);
    }

    @Override
    public List<AdvertisementEntity> findAll() {
        return advertisementRespository.findAll();
    }

    @Override
    public     List<AdvertisementEntity> findAdvertisementEntitiesByLinkContaining(String s) {
        return advertisementRespository.findAdvertisementEntitiesByLinkContaining(s);
    }

    @Override
    public AdvertisementEntity findAdvertisementEntityById(int id) {
        return advertisementRespository.findAdvertisementEntityById(id);
    }

    @Override
    public void deleteAdvertisementEntityById(int id) {
        advertisementRespository.deleteAdvertisementEntityById(id);
    }

    @Override
    public AdvertisementEntity updateAdById(String link, String photo, int id) {
        advertisementRespository.updateAdById(link, photo, id);
        return advertisementRespository.findAdvertisementEntityById(id);
    }
}
