package com.univaq.oosde.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CheckLoginController {
    /*@Autowired
    private AreaModel areaModel;*/
    @RequestMapping("/oosde")
    public ModelAndView main(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //session.invalidate();
        if (session.getAttribute("User") == null || session.getAttribute("User").equals("")) {
            ModelAndView mav = new ModelAndView("LoginPage");
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("DigitalLibrary");
            return mav;
        }
    }
}


