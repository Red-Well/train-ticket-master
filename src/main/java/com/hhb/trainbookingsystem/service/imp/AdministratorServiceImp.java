package com.hhb.trainbookingsystem.service.imp;

import com.hhb.trainbookingsystem.dao.AdministratorRespository;
import com.hhb.trainbookingsystem.entity.AdministratorEntity;
import com.hhb.trainbookingsystem.service.AdministratorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AdministratorServiceImp implements AdministratorService {
    @Autowired
    private AdministratorRespository administratorRespository;


    //根据账号找到管理员对象
    @Override
    public AdministratorEntity findAdtorByNameAndPwd(String name, String pwd){
        Optional<AdministratorEntity> administratorEntity = administratorRespository.findAdministratorEntityByNameAndPassword(name,pwd);
        if(administratorEntity!=null&&administratorEntity.isPresent()){
            return  administratorEntity.get();
        }
        else {
            return  null;
        }
    }


    //安全机制begin
    //登录并且设置token
    @Override
    public String login(String name, String password){
        Optional<AdministratorEntity> administratorEntity = administratorRespository.findAdministratorEntityByNameAndPassword(name, password);
        if (administratorEntity.isPresent()){
            String token = UUID.randomUUID().toString();
            AdministratorEntity administratorEntity1 = administratorEntity.get();
            administratorEntity1.setToken(token);
            administratorRespository.save(administratorEntity1);
            return token;
        }
        return StringUtils.EMPTY;
    }

    //token的机制验证
    public Optional<User> findByToken(String token) {
        Optional<AdministratorEntity> administratorEntity= administratorRespository.findAdministratorEntityByToken(token);
        if(administratorEntity.isPresent()){
            AdministratorEntity administratorEntity1 = administratorEntity.get();
            User user= new User(administratorEntity1.getName(), administratorEntity1.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(user);
        }
        return  Optional.empty();
    }

    //根据token找到相关的系统管理员
    @Override
    public AdministratorEntity findAdministratorByToken(String token) {
        return administratorRespository.findAdministratorEntityByToken(token).get();
    }

    //保存管理员
    @Override
    public void saveAdministrator(AdministratorEntity administratorEntity) {
        administratorRespository.save(administratorEntity);
    }
    //安全机制end

    //分页显示
    public Page<AdministratorEntity> findAdministratorPage(int page, int pageSize){
        page--;
        page = page < 0 ? 0 :page;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        return administratorRespository.findAll(pageable);
    }
}
