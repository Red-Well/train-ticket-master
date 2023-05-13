package com.hhb.trainbookingsystem.service;

import com.hhb.trainbookingsystem.entity.ContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ContactService {

    List<ContactEntity> findcontactors(int id);

    Page<ContactEntity> findallcontator(int id, Pageable pageable);

    ContactEntity findbyname(String name);

    void delecontactbyid(int id);

    void addcontatcor(ContactEntity c);

    void altercontactor(String phonenum,int id);


}
