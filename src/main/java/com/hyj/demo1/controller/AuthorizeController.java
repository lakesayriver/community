package com.hyj.demo1.controller;

import com.hyj.demo1.DTO.AccessTokenDTO;
import com.hyj.demo1.DTO.GithubUser;
import com.hyj.demo1.mapper.UserMapper;
import com.hyj.demo1.model.User;
import com.hyj.demo1.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String clientid;

    @Value("${github.client.secret}")
    private String clientsecret;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientid);
        accessTokenDTO.setClient_secret(clientsecret);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String token = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubuser = githubProvider.getGithubUser(token);
        System.out.println(token);
        if (request.getSession() != null) {
            //如果登录成功
            request.getSession().setAttribute("user", githubuser);
            System.out.println(githubuser.getName());

            User user = new User();
            user.setAccount_id(String.valueOf(githubuser.getId()));
            user.setName(githubuser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modify(user.getGmt_create());

            userMapper.insert(user);

            Cookie cookie = new Cookie("token", user.getToken());
            response.addCookie(cookie);


            return "redirect:/";
        } else {
            //登录失败
            return "redirect:/";
        }
    }
}
