package com.hhb.trainbookingsystem.controller;

import com.hhb.trainbookingsystem.entity.OrdinaryUserEntity;
import com.hhb.trainbookingsystem.service.OrdinaryUserService;
import com.hhb.trainbookingsystem.util.SM2KeyPair;
import com.hhb.trainbookingsystem.util.SM2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;

@Controller
@RequestMapping("")
public class LoginController {

    @Autowired
    private OrdinaryUserService userLogin;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String password,
                           @RequestParam String cardType,
                           @RequestParam String personId,
                           @RequestParam String email,
                           @RequestParam String realname,
                           @RequestParam String phonenum,
                           @RequestParam String personType) {
        OrdinaryUserEntity userEntity = new OrdinaryUserEntity();
        OrdinaryUserEntity user = userLogin.getuser(name);
        if (user == null) {
            SM2Util sm2 = new SM2Util();
            SM2KeyPair keyPair = sm2.generateKeyPair();
            byte[] data = sm2.encrypt(password, keyPair.getPublicKey());
            BigInteger privateKey = keyPair.getPrivateKey();
            userEntity.setName(name);
            userEntity.setPassword(data);
            userEntity.setType(cardType);
            userEntity.setPersonId(personId);
            userEntity.setEmail(email);
            userEntity.setRealname(realname);
            userEntity.setPhonenum(phonenum);
            userEntity.setPrivateKey(String.valueOf(privateKey));
//            userEntity.setPublicKey(keyPair.getPublicKey());
            if (personType.equals("成人"))
                userEntity.setIsstudent((byte) 0);
            else
                userEntity.setIsstudent((byte) 1);
            userLogin.createOrdinaryUserEntity(userEntity);
        } else {
            System.out.println("用户名已经被注册");

        }
        return "redirect:/login";
    }


    @PostMapping("/admin/login")
    public String logintry(@RequestParam("username") String username, @RequestParam("password") String password,
                           HttpSession session, RedirectAttributes attributes) {

        /** Logger实例 */


        OrdinaryUserEntity ordinaryuser = userLogin.getuser(username);
        if (ordinaryuser != null) {
//            ordinaryuser.setPassword(null);
            BigInteger privateKey = new BigInteger(ordinaryuser.getPrivateKey());
            byte[] data = ordinaryuser.getPassword();
            SM2Util sm2 = new SM2Util();
            if (password.equals(sm2.decrypt(data, privateKey))) {
                session.setAttribute("user", ordinaryuser);
                return "redirect:/index";
            }
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            // 打印出错误堆栈信息
        }
        return "redirect:/login";
    }

    @ResponseBody
    @PostMapping("/admin/logout")
    public void logout(HttpSession session) {
        session.removeAttribute("user");

    }

}
