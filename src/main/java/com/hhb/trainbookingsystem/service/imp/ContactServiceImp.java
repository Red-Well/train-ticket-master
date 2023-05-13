package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.annotation.Operation;
import com.hhb.trainbookingsystem.dao.ContactRespository;
import com.hhb.trainbookingsystem.entity.ContactEntity;
import com.hhb.trainbookingsystem.enums.OperationType;
import com.hhb.trainbookingsystem.enums.OperationUnit;
import com.hhb.trainbookingsystem.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImp implements ContactService {

    @Autowired
    ContactRespository contactRespository;

    @Override
    public Page<ContactEntity> findallcontator(int id, Pageable pageable) {
        Page<ContactEntity> contactEntityPage=contactRespository.findallcontactpage(id,pageable);
        return contactEntityPage;
    }

    @Override
    public ContactEntity findbyname(String name) {
        return contactRespository.findContactEntityByName(name);
    }

    @Operation(value = "删除联系人",level = 2,operationUnit = OperationUnit.USER,operationType = OperationType.DELETE)
    @Override
    public void delecontactbyid(int id) {
        contactRespository.deleteById(id);
    }

    @Operation(value = "增加联系人",level = 2,operationUnit = OperationUnit.USER,operationType = OperationType.INSERT)
    @Override
    public void addcontatcor(ContactEntity c) {
        contactRespository.save(c);
    }

    @Operation(value = "修改联系人信息",level = 2,operationType = OperationType.UPDATE,operationUnit = OperationUnit.USER)
    @Override
    public void altercontactor(String phonenum, int id) {
        contactRespository.updatecontactor(phonenum,id);
    }

    @Operation(value = "查看联系人",level = 1,operationType = OperationType.SELETE,operationUnit = OperationUnit.USER)
    @Override
    public List<ContactEntity> findcontactors(int id) {
        List<ContactEntity> contactEntities=contactRespository.findContactEntitiesByUserId(id);
        return contactEntities;
    }


}
