package com.univaq.oosde.controller;

import com.univaq.oosde.model.Artwork;
import com.univaq.oosde.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class MainController {
    @RequestMapping("/DigitalLibrary")
    public ModelAndView main(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ModelAndView("LoginPage");
        } else {
            User user = (User) session.getAttribute("User");
            boolean flag = (user.isAdministrator() || user.isManager());
            List<Artwork> artworks = Artwork.getAllArtworks(flag);
            ModelAndView mav = new ModelAndView("DigitalLibrary");
            mav.addObject("artworks", artworks);
            return mav;
        }
    }

    @GetMapping(value = "/DigitalLibrary/SearchResults")
    public ModelAndView query(@RequestParam("search") String search, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        if (search.equals("")) {
            backToHomepage();
        }
        List<Artwork> artworkResultList = Artwork.searchArtworkBySearchString(search);
        ModelAndView mav = new ModelAndView("SearchResults");
        mav.addObject("artworkResultList", artworkResultList);
        mav.addObject("searchString", search);
        return mav;

    }

    @PostMapping(value = "/DigitalLibrary/SearchResults")
    public String backToHomepage() {
        return "redirect:/DigitalLibrary";
    }
}


