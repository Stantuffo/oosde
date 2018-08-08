package com.univaq.oosde.controller;

import com.univaq.oosde.entity.ConnectionClass;
import com.univaq.oosde.entity.user;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class LoginController {
    /*@Autowired
    private AreaModel areaModel;*/

    @RequestMapping(value = "/oosde/login", method = RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        try {
            System.out.println("AAA");
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'";
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                user user = new user(resultSet);
                RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
                ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
                HttpServletRequest request = attributes.getRequest();
                HttpSession session = request.getSession(true);
                session.setAttribute("User", user);
                return "redirect:/oosde";

            } else {
                //ERRORE - Utente non trovato nel db
                //Redirect alla main page che rimanderà alla login
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*HttpSession session = request.getSession();
        if (session.getAttribute("User") == null || session.getAttribute("User").equals("")) {
            ModelAndView mav = new ModelAndView("login");
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("main");
            return mav;
        }*/
        //ModelAndView mav = new ModelAndView("login");
        //return mav;
        return "redirect:/oosde";
    }
}


