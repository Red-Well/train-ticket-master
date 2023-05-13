package com.hhb.trainbookingsystem.controller;

import com.hhb.trainbookingsystem.entity.OrdinaryUserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 内容：用户端的项目导航
 * index 首页
 * search 查询车票
 *
 */
@Controller
public class MainController{

    /**
     * 首页url
     * @return
     */
    @RequestMapping("/404.html")
    public String errorPage(){
        return "404.html";
    }
    @RequestMapping("/index")
    public String getIndex(HttpSession session, Model model){
        OrdinaryUserEntity user=(OrdinaryUserEntity) session.getAttribute("user");
        if(user!=null)
            model.addAttribute("names",user.getName());
        return "index";
    }

    /**
     * 查询(新)页面
     * @return
     */
    @GetMapping("/search_new")
    public String search_new(HttpSession session, Model model){

        OrdinaryUserEntity user=(OrdinaryUserEntity) session.getAttribute("user");
        if(user!=null)
            model.addAttribute("names",user.getName());
        return "search_new";
    }

    /**
     * 查询页面
     * @return
     */
    @GetMapping("/search")
    public String search(HttpSession session, Model model){

        OrdinaryUserEntity user=(OrdinaryUserEntity) session.getAttribute("user");
        if(user!=null)
            model.addAttribute("names",user.getName());
        return "search";
    }

    @GetMapping("/changeticket/pay")
    public String paychangeticket(Model model,HttpSession session){
        OrdinaryUserEntity user=(OrdinaryUserEntity) session.getAttribute("user");
        if(user!=null){
            model.addAttribute("names",user.getName());
        }
        return "gaiqianpay";
    }


    /**
     * 买票页面
     * @return
     */
    @RequestMapping("/search_new")
    public String getSearch_new() {
        return "search_new";
    }
}
