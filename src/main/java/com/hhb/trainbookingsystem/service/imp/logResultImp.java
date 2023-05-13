package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.dao.LogResultRepository;
import com.hhb.trainbookingsystem.entity.logResult.LogResultEntity;
import com.hhb.trainbookingsystem.service.LogResultService;
import org.springframework.stereotype.Service;

@Service
public class logResultImp implements LogResultService {
    public final LogResultRepository logResultRepository;

    public logResultImp(LogResultRepository logResultRepository) {
        this.logResultRepository = logResultRepository;
    }

    @Override
    public LogResultEntity findLogResultEntityByUsername(String username) {
        return logResultRepository.findLogResultEntityByUsername(username);
    }

    @Override
    public String[] findLogResultByUsername(String username) {
        LogResultEntity logResultEntity = logResultRepository.findLogResultEntityByUsername(username);
        return logResultEntity.getOps().split("/");
    }
}
