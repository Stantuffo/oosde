package com.univaq.oosde.controller;

import com.univaq.oosde.model.ConnectionClass;
import com.univaq.oosde.model.Artwork;
import com.univaq.oosde.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Controller
public class MainController {
    /*@Autowired
    private AreaModel areaModel;*/
    @RequestMapping("/DigitalLibrary")
    public ModelAndView main(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        if (session.getAttribute("User") == null || session.getAttribute("User").equals("")) {
            return new ModelAndView("LoginPage");
        } else {
        User user = (User) session.getAttribute("User");
        Artwork a = new Artwork();
        boolean flag = (user.isAdministrator() || user.isManager());
        List<Artwork> artworks = a.getAllArtworks(flag);
        ModelAndView mav = new ModelAndView("DigitalLibrary");
        mav.addObject("artworks", artworks);
        return mav;
        }
    }

    @GetMapping(value = "/DigitalLibrary/SearchResults")
    public ModelAndView query(@RequestParam("search") String search, HttpServletRequest request) throws SQLException {
        if (search.equals("")){
            backToHomepage();
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("User") == null || session.getAttribute("User").equals("")) {
            return new ModelAndView("/DigitalLibrary");
        } else {
            List<Artwork> artworkResultList = Artwork.searchArtworkBySearchString(search);
            ModelAndView mav = new ModelAndView("SearchResults");
            mav.addObject("artworkResultList", artworkResultList);
            mav.addObject("searchString", search);
            return mav;
        }
    }
    @PostMapping(value = "/DigitalLibrary/SearchResults")
    public String backToHomepage(){
        return "redirect:/DigitalLibrary";
    }
}


