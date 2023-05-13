
package com.hhb.trainbookingsystem.controller.restController;

import com.hhb.trainbookingsystem.entity.AdvertisementEntity;
import com.hhb.trainbookingsystem.entity.OrdinaryUserEntity;
import com.hhb.trainbookingsystem.entity.TicketManagerEntity;
import com.hhb.trainbookingsystem.service.AdministratorService;
import com.hhb.trainbookingsystem.service.AdvertisementService;
import com.hhb.trainbookingsystem.service.OrdinaryUserService;
import com.hhb.trainbookingsystem.service.TicketManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/managercenter")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ManagerCenterRestController {
    @Autowired
    private TicketManagerService ticketManagerService;
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private OrdinaryUserService ordinaryUserService;

    @Autowired
    private AdministratorService administratorService;


    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    //获得相关的售票管理员之后然后进行分页
    @PostMapping("/ticketmanager/page/{page}")
    public Map<String, Object> findPeoplesWithPage(@PathVariable int page) {
        int pageSize = 10;
        page = page == 0 ? 1 : page;
        Page<TicketManagerEntity> pageInfo = ticketManagerService.findTicketManagers(page, pageSize);
        Map<String, Object> mapper = new HashMap<>();
        mapper.put("totalElements", pageInfo.getTotalElements());
        mapper.put("onePageContent", pageInfo.getContent());
        return mapper;
    }


    //获取所有售票管理员
    @GetMapping("/ticketmanager")
    public List<TicketManagerEntity> getAllTicketManager() {
        return ticketManagerService.findALL();
    }

    //获取某管理员的信息
    @GetMapping("/findticketmanager/{id}")
    public TicketManagerEntity getaTicketManager(@PathVariable int id) {
        return ticketManagerService.findTicketManagerEntityById(id);
    }

    //增加售票管理员
    @PostMapping("/addticketmanager")
    public TicketManagerEntity addTicketManager(@RequestBody TicketManagerEntity t) {
        return ticketManagerService.save(t);
    }

    //修改售票管理员
    @PostMapping("/editticketmanager")
    public TicketManagerEntity editticketmanager(@RequestParam("name") String name, @RequestParam("password") String password,
                                                 @RequestParam("staffId") String staffId, @RequestParam("id") int id) {
        System.out.println(name + "name");
        return ticketManagerService.updateTicketManagerById1(name, password, staffId, id);
    }

    //查找售票管理员
    @PostMapping("/findticketmanager")
    public List<TicketManagerEntity> findticketmanager(@RequestParam(value = "name", required = false) String name) {
        return ticketManagerService.findTicketManagerEntitiesByNameContainingOrStaffIdContaining(name);
    }

    //删除售票管理员
    @DeleteMapping("/deleteticketmanager/{id}")
    public void deleteById(@PathVariable int id) {
        ticketManagerService.deleteTicketManagerEntityById(id);
    }

    //查看所有用户
    @GetMapping("/user")
    public List<OrdinaryUserEntity> getAllUsers() {
        return ordinaryUserService.findAll();
    }

    //删除用户
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUserById(@PathVariable int id) {
        ordinaryUserService.deleteOrdinaryUserEntityById(id);
    }

    //查找用户
    @PostMapping("/findUser")
    public List<OrdinaryUserEntity> findUser(@RequestParam(value = "name", required = false) String name) {
        return ordinaryUserService.findOrdinaryUserEntitiesByNameContaining(name);
    }

    //编辑用户
    @PostMapping("/editUser")
    public OrdinaryUserEntity editUser(@RequestParam("name") String name,
                                       @RequestParam("personId") String staffId, @RequestParam("is_student") Byte is_student,
                                       @RequestParam("credit") Byte credit, @RequestParam("id") int id) {
        System.out.println("update");
        return ordinaryUserService.updateUserById(name, staffId, is_student, credit, id);
    }

    //显示所有广告信息
    @GetMapping("/ads")
    public List<AdvertisementEntity> getAllAdvertisement() {
        return advertisementService.findAll();
    }

    //显示所有广告信息
    @GetMapping("/showads")
    public List<AdvertisementEntity> showAdvertisement() {
        List<AdvertisementEntity> advertisementEntityLists = advertisementService.findAll();
        if (advertisementEntityLists.toArray().length >= 4) {
            List<AdvertisementEntity> showAds = advertisementEntityLists.subList(0, 4);
            System.out.println("找到");
            return showAds;
        }
        return null;
    }

    //删除广告
    @DeleteMapping("/deleteAd/{id}")
    public void deleteAdById(@PathVariable int id) {
        advertisementService.deleteAdvertisementEntityById(id);
    }

    //查找广告
    @PostMapping("/findAd")
    public List<AdvertisementEntity> findAd(@RequestParam(value = "link", required = false) String name) {
        return advertisementService.findAdvertisementEntitiesByLinkContaining(name);
    }

    //新增广告
    @PostMapping("/addAd")
    public AdvertisementEntity addAd(MultipartFile file, HttpServletRequest req, HttpServletResponse response, @RequestParam(value = "link", required =
            false) String link, Map<String, Object> map) throws IOException {
        AdvertisementEntity advertisementEntity = new AdvertisementEntity();
        String format = sdf.format(new Date());
        String realPath = req.getServletContext().getRealPath("/upload") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String localPath = "D:/java/train/TrainBookingSystem/src/main/resources/static/picture/ad";
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        System.out.println(Paths.get(newName));
        file.transferTo(Paths.get(localPath + "/" + newName));
        //file.transferTo(new File(folder,localPath+"/"+newName));
        String url = "http://localhost:8080/picture/ad/" + Paths.get(newName);
        advertisementEntity.setPhoto(url);
        advertisementEntity.setLink(link);
        return advertisementService.save(advertisementEntity);
    }

    //编辑广告
    @PostMapping("/editAd")
    public AdvertisementEntity editAd(MultipartFile file, HttpServletRequest req, @RequestParam(value = "link", required =
            false) String link, @RequestParam("id") int id) throws IOException {

        String format = sdf.format(new Date());
        String realPath = req.getServletContext().getRealPath("/upload") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String localPath = "D:/java/train/TrainBookingSystem/src/main/resources/static/picture/ad";
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        System.out.println(Paths.get(newName));
        file.transferTo(Paths.get(localPath + "/" + newName));
        //file.transferTo(new File(folder,localPath+"/"+newName));
        String url = "http://localhost:8080/picture/ad/" + Paths.get(newName);
        return advertisementService.updateAdById(link, url, id);
    }

}
