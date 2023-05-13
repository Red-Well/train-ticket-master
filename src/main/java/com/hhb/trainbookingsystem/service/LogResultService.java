package com.hhb.trainbookingsystem.service;

import com.hhb.trainbookingsystem.entity.logResult.LogResultEntity;

public interface LogResultService {
    public LogResultEntity findLogResultEntityByUsername(String username);
    public String[] findLogResultByUsername(String username);
}
