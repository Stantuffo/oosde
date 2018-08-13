package com.univaq.oosde.controller;

import com.univaq.oosde.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.List;
import java.util.TreeMap;

@Controller
public class AuthorController {

    @GetMapping("DigitalLibrary/NewAuthor")
    public ModelAndView viewArtworkPage(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("User");
        }
        if (user.isManager() || user.isAdministrator()) {
            ModelAndView mav = new ModelAndView("NewAuthor");
            return mav;
        }
        else{
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }

    @RequestMapping(value = "/DigitalLibrary/AddAuthor", method = RequestMethod.POST)
    public ModelAndView insertNewAuthor(@RequestParam("inputName") String name, @RequestParam("inputSurname") String surname, @RequestParam("inputBirthDate") String birthDate, @RequestParam("inputBirthPlace") String birthPlace, @RequestParam("inputBirthCountry") String birthCountry, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("User");
        }
        if (!user.isManager() || !user.isAdministrator()) {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        else {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            if (name.equals("") || surname.equals("")) {
                return new ModelAndView("redirect:/DigitalLibrary/NewAuthor");
            } else {
                String sql = "INSERT INTO author(name, surname, birth_date, birth_place, birth_country) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2,surname);
                if (!birthDate.equals("")) {
                    statement.setString(3, birthDate);
                } else {
                    statement.setNull(3, Types.DATE);
                }
                if (!birthPlace.equals("")) {
                    statement.setString(4, birthPlace);
                } else {
                    statement.setNull(4, Types.VARCHAR);
                }
                if (!birthCountry.equals("")) {
                    statement.setString(5, birthPlace);
                } else {
                    statement.setNull(5, Types.VARCHAR);
                }
                System.out.println(sql);
                System.out.println(statement);
                int a = statement.executeUpdate();
                //int resultSet = statement.executeUpdate(sql);
                System.out.println(a);
                return new ModelAndView("AuthorAdded");
            }
        }
    }
}