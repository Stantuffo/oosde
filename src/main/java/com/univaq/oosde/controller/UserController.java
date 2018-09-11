package com.univaq.oosde.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController {

    @RequestMapping("DigitalLibrary/UserAccount")
    public ModelAndView utente(){
        return new ModelAndView("UserOverview");
    }

    @RequestMapping("DigitalLibrary/Logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession();
            if(session.getAttribute("user") != null){
                session.removeAttribute("user");
            }
        return new ModelAndView("UserOverview");
    }
}
