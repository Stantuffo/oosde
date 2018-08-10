package com.univaq.oosde.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @RequestMapping("DigitalLibrary/UserAccount")
    public ModelAndView utente(){
        ModelAndView mav = new ModelAndView("UserOverview");
        return mav;
    }
}
