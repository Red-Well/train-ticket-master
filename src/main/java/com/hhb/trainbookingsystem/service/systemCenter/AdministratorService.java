package com.hhb.trainbookingsystem.service.systemCenter;

import com.hhb.trainbookingsystem.dao.AdvertisementRespository;
import com.hhb.trainbookingsystem.dao.OrdinaryUserRepository;
import com.hhb.trainbookingsystem.dao.TicketManagerRepository;
import com.hhb.trainbookingsystem.entity.AdvertisementEntity;
import com.hhb.trainbookingsystem.entity.OrdinaryUserEntity;
import com.hhb.trainbookingsystem.entity.TicketManagerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdministratorService {
    @Autowired
    public OrdinaryUserRepository ordinaryUserRepository;

    @Autowired
    public TicketManagerRepository ticketManagerRepository;


    @Autowired
    public AdvertisementRespository advertisementRespository;

    /**
     * 1. 保存一个普通用户
     */
    public void saveOrdinaryUser(OrdinaryUserEntity ordinaryUserEntity){
        ordinaryUserRepository.save(ordinaryUserEntity);
    }

    /**
     * 2. 删除一个普通用户
     */
    public void deleteOrdinaryUserById(int id){
        ordinaryUserRepository.deleteOrdinaryUserEntityById(id);
    }

    /**
     * 3. 分页显示用户的列表
     */
    public Page<OrdinaryUserEntity> findOrdinaryUsersInPage(int page, int pageSize){
        page--;
        page = page < 0 ? 0 : page;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        return ordinaryUserRepository.findAll(pageable);
    }

    /**
     * 4. 多条件查询一个普通用户
     */
    public List<OrdinaryUserEntity> findPeoplesWithSpecification(OrdinaryUserEntity ordinaryUserEntity){
        Specification<OrdinaryUserEntity> specification = new Specification<OrdinaryUserEntity>() {
            @Override
            public Predicate toPredicate(Root<OrdinaryUserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //所有的断言及条件
                List<Predicate> predicates = new ArrayList<>();

                //匹配名字
                if (ordinaryUserEntity.getName() != null){
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + ordinaryUserEntity.getName() + "%"));
                }

                //匹配电话
                if (ordinaryUserEntity.getPhonenum() != null){
                    predicates.add(criteriaBuilder.like(root.get("phonenum"), "%" + ordinaryUserEntity.getPhonenum() + "%"));
                }

                //匹配身份证
                if (ordinaryUserEntity.getPersonId() != null){
                    predicates.add(criteriaBuilder.like(root.get("personId"), "%" + ordinaryUserEntity.getPersonId() + "%"));
                }

                //匹配邮箱
                if (ordinaryUserEntity.getEmail() != null){
                    predicates.add(criteriaBuilder.like(root.get("email"), "%" + ordinaryUserEntity.getEmail() + "%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

            }
        };
        List<OrdinaryUserEntity> ordinaryUserEntities = ordinaryUserRepository.findAll(specification);
        return ordinaryUserEntities;
    }


    /**
     * 1. 保存一个售票端管理员
     */
    public void saveTicketUser(TicketManagerEntity ticketManagerEntity){
        ticketManagerRepository.save(ticketManagerEntity);
    }

    /**
     * 2. 删除一个售票端管理员
     */
    public void deleteTicketUser(int id){
        ticketManagerRepository.deleteById(id);
    }

    /**
     * 3. 分页显示售票管理员列表
     */
    public Page<TicketManagerEntity> findTicketUsersInPage(int page, int pageSize){
        page--;
        page = page < 0 ? 0 : page;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        return ticketManagerRepository.findAll(pageable);
    }

    /**
     * 4. 多条件查询售票管理员
     */
    public List<TicketManagerEntity> findTicketUsersWithSpecification(TicketManagerEntity ticketManagerEntity){
        Specification<TicketManagerEntity> specifications = new Specification<TicketManagerEntity>() {
            @Override
            public Predicate toPredicate(Root<TicketManagerEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //所有的断言及条件
                List<Predicate> predicates = new ArrayList<>();

                //匹配名字
                if (ticketManagerEntity.getName() != null){
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + ticketManagerEntity.getName() + "%"));
                }

                //匹配staffId
                if (ticketManagerEntity.getStaffId() != null){
                    predicates.add(criteriaBuilder.like(root.get("staffId"), ticketManagerEntity.getStaffId()));
                }

                //匹配身usertype
                if (ticketManagerEntity.getUserType() != null){
                    predicates.add(criteriaBuilder.like(root.get("userType"), ticketManagerEntity.getUserType()));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

            }
        };
        List<TicketManagerEntity> ticketManagerEntities = ticketManagerRepository.findAll(specifications);
        return ticketManagerEntities;
    }


    /**
     * 1. 保存广告
     */
    public void saveAd(AdvertisementEntity advertisementEntity){
        advertisementRespository.save(advertisementEntity);
    }

    /**
     * 2. 删除广告
     */
    public void deleteAd(int id){
        advertisementRespository.deleteById(id);
    }

    /**
     * 3. 广告列表
     */
    public Page<AdvertisementEntity> findAdInPage(int page, int pageSize){
        page--;
        page = page < 0 ? 0 : page;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        return advertisementRespository.findAll(pageable);
    }
}
