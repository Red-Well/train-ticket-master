package com.hhb.trainbookingsystem.controller;
import com.hhb.trainbookingsystem.entity.TicketManagerEntity;
import com.hhb.trainbookingsystem.service.TicketManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.Map;



/**
 * 内容： 售票端
 * 内容：售票端管理员的登录和退出登录
 */
@Controller
@Api(tags = "票务端登陆验证",description = "票务端登陆验证API")
public class TicketCenterController {
    public TicketManagerEntity ticketManagerEntity = new TicketManagerEntity();
    @Autowired
    public TicketManagerService ticketManagerService;
    @ApiIgnore
    @RequestMapping("ticketLogin")
    public String ticketLogin(){
        return "ticketlogin";
    }
    //检查管理员登录
    @ApiOperation("票务管理员登陆验证")
    @RequestMapping("ticketManager/login")
    public String checkManager(@RequestParam("name") String name,
                               @RequestParam("pw") String pw,
                               Map<String,Object> map,
                               Model model,
                               HttpSession session) throws Exception {
        final Logger logger = LoggerFactory.getLogger(LoginController.class);
        ticketManagerEntity = ticketManagerService.findTicketManagerEntityByNameAndPassword(name, pw);
        if(ticketManagerEntity!=null ) {
            logger.info("用户"+name+"登录");
            System.out.println("用户"+name+"登录");
            //doPost("127.0.0.1:8080", String.valueOf(ticketManagerEntity.getId()));
            model.addAttribute("ticketManager",ticketManagerEntity);
            session.setAttribute("ticketManager",ticketManagerEntity);
            return "ticketCenter";

        }
        else{
            logger.error("密码错误！");
            System.out.println("no user");
            map.put("msg1","用户名密码错误");
            model.addAttribute("msg1","用户名密码错误");
            return "redirect:/ticketLogin";
        }
    }
    @ApiIgnore
    @RequestMapping("/ticketLoginBack")
    public String backLogin(){
        return "redirect:/ticketLogin";
    }

}
