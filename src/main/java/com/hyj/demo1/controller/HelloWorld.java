package com.hyj.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloWorld {
    @RequestMapping(value = "/hello")

   public String helloWorld(@RequestParam(name="name",required=false,defaultValue = "you")String name, Model model){
        model.addAttribute("name",name);

        return "helloWorld";
    }
}
