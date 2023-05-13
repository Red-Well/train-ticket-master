package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.annotation.Operation;
import com.hhb.trainbookingsystem.annotation.OperationLogDetail;
import com.hhb.trainbookingsystem.dao.OrdinaryUserRepository;
import com.hhb.trainbookingsystem.entity.OrdinaryUserEntity;
import com.hhb.trainbookingsystem.enums.OperationType;
import com.hhb.trainbookingsystem.enums.OperationUnit;
import com.hhb.trainbookingsystem.service.OrdinaryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdinaryUserServiceImp implements OrdinaryUserService {
    @Autowired
    OrdinaryUserRepository ordinaryUserRepository;

    @OperationLogDetail(detail = "通过账号登录",level = 3,operationType = OperationType.SELETE,operationUnit = OperationUnit.USER)
    @Override
    public OrdinaryUserEntity checkuser(String name, String password) {
//        SM2Util sm2 = new SM2Util();
//        SM2KeyPair keyPair = sm2.generateKeyPair();
//        byte[] data = sm2.encrypt(M, keyPair.getPublicKey());
        OrdinaryUserEntity ordinaryuser=ordinaryUserRepository.TrygetUser(name,password);
        return ordinaryuser;
    }

    @Override
    public OrdinaryUserEntity getuser(String username) {
        OrdinaryUserEntity ordinaryuser=ordinaryUserRepository.finduserbyname(username);
        return ordinaryuser;
    }

    @Override
    public List<OrdinaryUserEntity> findAll() {
        return ordinaryUserRepository.findAll();
    }

    @Override
    public void deleteOrdinaryUserEntityById(int id) {
        ordinaryUserRepository.deleteOrdinaryUserEntityById(id);
    }

    @Override
    public OrdinaryUserEntity findOrdinaryUserEntityById(int id) {
        return ordinaryUserRepository.findOrdinaryUserEntityById(id);
    }

    @Override
    public List<OrdinaryUserEntity> findOrdinaryUserEntitiesByNameContaining(String s) {
        return ordinaryUserRepository.findOrdinaryUserEntitiesByNameContaining(s);
    }

    @Override
    public OrdinaryUserEntity updateUserById(String name,  String person_id, Byte is_student, Byte credit, int id) {
        ordinaryUserRepository.updateUserById(name, person_id, is_student, credit, id);
        return ordinaryUserRepository.findOrdinaryUserEntityById(id);
    }

    @Override
    @Operation(value ="注册" ,level = 4,operationType = OperationType.INSERT,operationUnit = OperationUnit.USER)
    public void createOrdinaryUserEntity(OrdinaryUserEntity userEntity){
        ordinaryUserRepository.save(userEntity);
    }

    @Operation(value ="修改个人信息" ,level = 4,operationType = OperationType.UPDATE,operationUnit = OperationUnit.USER)
    @Override
    public void edituinfo(String phonenum, String name) {
        ordinaryUserRepository.updatebynames(phonenum,name);
    }


}
