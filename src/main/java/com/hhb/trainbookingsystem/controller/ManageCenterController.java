package com.hhb.trainbookingsystem.controller;

import com.hhb.trainbookingsystem.entity.AdministratorEntity;
import com.hhb.trainbookingsystem.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class    ManageCenterController {
    public AdministratorEntity Administrator = new AdministratorEntity();
    @Autowired
    private AdministratorService administratorService;
    //索引到管理员登陆
    @RequestMapping("/managerLogin")
    public String managerLogin(){
        return "managerlogin";
    }
    //检查管理员登录
    @RequestMapping("manager/login")
    public String checkManager(@RequestParam("name") String name,
                               @RequestParam("pw") String pw,
                               Map<String,Object> map,
                               HttpSession session){
        Administrator = administratorService.findAdtorByNameAndPwd(name,pw);
        if(Administrator!=null ) {
            System.out.println("用户"+name+"登录");
            session.setAttribute("Administrator",Administrator);
            return "managercenter";
        }
        else{
            System.out.println("no user");
            map.put("msg1","用户名密码错误");
            return "redirect:/managerLogin";
        }
    }
    @RequestMapping("/managerBackLogin")
    public String backLogin(){
        System.out.println("ss");
        return "redirect:/managerLogin";
    }


}
