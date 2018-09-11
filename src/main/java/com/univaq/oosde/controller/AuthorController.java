package com.univaq.oosde.controller;

import com.univaq.oosde.model.Author;
import com.univaq.oosde.model.ConnectionClass;
import com.univaq.oosde.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

@Controller
public class AuthorController {

    @GetMapping("DigitalLibrary/NewAuthor")
    public ModelAndView viewArtworkPage(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        if (user.isManager() || user.isAdministrator()) {
            return new ModelAndView("NewAuthor");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }

    @RequestMapping(value = "/DigitalLibrary/AddAuthor", method = RequestMethod.POST)
    public ModelAndView insertNewAuthor(@RequestParam("inputName") String name, @RequestParam("inputSurname") String surname, @RequestParam("inputBirthDate") String birthDate, @RequestParam("inputBirthPlace") String birthPlace, @RequestParam("inputBirthCountry") String birthCountry, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else
            return new ModelAndView("redirect:/DigitalLibrary");
        if (!user.isManager() || !user.isAdministrator()) {
            return new ModelAndView("redirect:/DigitalLibrary");
        } else {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            if (name.equals("") || surname.equals("")) {
                return new ModelAndView("redirect:/DigitalLibrary/NewAuthor");
            } else {
                boolean success = Author.insertNewAuthor(connection, name, surname, birthDate, birthPlace, birthCountry);
                if (success) {
                    return new ModelAndView("AuthorAdded");
                } else return new ModelAndView("AuthorAddingError");
            }
        }
    }

    @RequestMapping(value = "/DigitalLibrary/AddAuthor", method = RequestMethod.GET)
    public ModelAndView insertNewAuthor() {
        return new ModelAndView("redirect:/DigitalLibrary");
    }
}