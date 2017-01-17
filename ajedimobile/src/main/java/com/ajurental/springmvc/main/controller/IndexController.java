package com.ajurental.springmvc.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
@RequestMapping("/")
public class IndexController {
 
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        return "web/index";
    }
    
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login() {
    	return "loginForm";
    }
}
