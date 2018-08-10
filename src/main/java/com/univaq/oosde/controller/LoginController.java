package com.univaq.oosde.controller;

import com.univaq.oosde.model.ConnectionClass;
import com.univaq.oosde.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class LoginController {

    @RequestMapping(value = "/DigitalLibrary/login", method = RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'";
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession(true);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            User user = new User(resultSet);
            session.setAttribute("User", user);
            return "redirect:/DigitalLibrary";

        } else {
            session.setAttribute("Errore", true);
            //ERRORE - Utente non trovato nel db
            //Redirect alla main page che rimanderà alla login
        }
        return "redirect:/DigitalLibrary";
    }

    @RequestMapping(value = "/DigitalLibrary/login", method = RequestMethod.GET)
    public String login() {
        return "redirect:/DigitalLibrary";
    }
}


