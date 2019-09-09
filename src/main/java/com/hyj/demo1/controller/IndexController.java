package com.hyj.demo1.controller;

import com.hyj.demo1.mapper.UserMapper;
import com.hyj.demo1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {

        Cookie[] requestCookies = request.getCookies();
        if (requestCookies.length > 0) {
            for (Cookie requestCookie : requestCookies) {
                if (requestCookie.getName().equals("token")) {
                    String token = requestCookie.getValue();
                    System.out.println(token);
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        return "index";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
}
