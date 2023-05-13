package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.dao.LogRespository;
import com.hhb.trainbookingsystem.entity.LoginfoEntity;
import com.hhb.trainbookingsystem.service.LoginfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginfoServiceImp implements LoginfoService {
    @Autowired
    LogRespository logRespository;


    @Override
    public void insertLogininfo(LoginfoEntity loginfoEntity) {
        logRespository.save(loginfoEntity);
    }
}
