/**
 内容：票务管理员的Service层实现
 **/
package com.hhb.trainbookingsystem.service.imp;
import com.hhb.trainbookingsystem.dao.TicketManagerRepository;
import com.hhb.trainbookingsystem.entity.TicketManagerEntity;
import com.hhb.trainbookingsystem.service.TicketManagerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketManagerServiceImp implements TicketManagerService {
    private final TicketManagerRepository tmr;

    public TicketManagerServiceImp(TicketManagerRepository tmr) {
        this.tmr = tmr;
    }

    @Override
    public List<TicketManagerEntity> findALL() {
        return tmr.findAll();
    }

    @Override
    public TicketManagerEntity save(TicketManagerEntity t) {
        return tmr.save(t);
    }

    @Override
    public TicketManagerEntity findTicketManagerEntityById(int id) {
      return  tmr.findTicketManagerEntityById(id);
    }

    @Override
    public TicketManagerEntity findTicketManagerEntityByNameContains(String name) {
        return tmr.findTicketManagerEntityByNameContains(name);
    }

    @Override
    public List<TicketManagerEntity> findTicketManagerEntitiesByNameContainingOrStaffIdContaining(String name) {
        return tmr.findTicketManagerEntitiesByNameContaining(name);
    }

    @Override
    public TicketManagerEntity findTicketManagerEntityByNameAndPassword(String name, String pw) {
        return tmr.findTicketManagerEntityByNameAndPassword(name, pw);
    }


    @Override
    public TicketManagerEntity findTicketManagerEntityByStaffIdNotContains(String staff_id) {
        return tmr.findTicketManagerEntityByStaffIdNotContains(staff_id);
    }

    @Override
    public void deleteTicketManagerEntityById(int id) {
        tmr.deleteTicketManagerEntityById(id);
    }

    @Override
    public TicketManagerEntity updateTicketManagerById1(String name, String password, String staffId, int id) {
        tmr.updateTicketManagerById1(name, password, staffId, id);
        return tmr.findTicketManagerEntityById(id);
    }

    @Override
    public TicketManagerEntity updateTicketManagerById2(String name, String password, int id) {
        tmr.updateTicketManagerById2(name, password, id);
        return tmr.findTicketManagerEntityById(id);
    }


    @Override
    public Page<TicketManagerEntity> findTicketManagers(int page, int pageSize) {
        page--;
        page = page < 0 ? 0 : page;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        return tmr.findAll(pageable);
    }
}
