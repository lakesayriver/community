package com.hyj.demo1.controller;

import com.hyj.demo1.DTO.AccessTokenDTO;
import com.hyj.demo1.DTO.GithubUser;
import com.hyj.demo1.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("2b7a8608d5a3fc377ed2");
        accessTokenDTO.setClient_secret("2e9b9bf5ec0da941a0545d2a76138dd68dcdf998");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String token = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println(githubProvider.getGithubUser(token).getName());
        return "index";
    }
}
