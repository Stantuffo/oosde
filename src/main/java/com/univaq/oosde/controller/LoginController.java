package com.univaq.oosde.controller;

import com.univaq.oosde.model.ConnectionClass;
import com.univaq.oosde.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Connection;
import java.sql.SQLException;

@Controller
public class LoginController {

    @RequestMapping(value = "/DigitalLibrary/Login", method = RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        User.login(connection, email, password);
        return "redirect:/DigitalLibrary";
    }

    @RequestMapping(value = "/DigitalLibrary/Login", method = RequestMethod.GET)
    public String login() {
        return "redirect:/DigitalLibrary";
    }
}


