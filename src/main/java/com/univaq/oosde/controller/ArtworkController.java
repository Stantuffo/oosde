package com.univaq.oosde.controller;

import com.univaq.oosde.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ArtworkController {

    @GetMapping("DigitalLibrary/Opera")
    public ModelAndView viewOperaPage(@RequestParam int artId) throws SQLException {
        Artwork art = Artwork.getArtworkById(artId);
        List<Image> imgs = art.getPagesOfOpera(artId);
        int authId = art.getAuthor_id();
        Author auth = Author.getAuthorById(authId);
        ModelAndView mav = new ModelAndView("ArtworkOverview");
        mav.addObject("artwork", art);
        mav.addObject("author", auth);
        mav.addObject("pages", imgs);
        return mav;
    }

    @RequestMapping("DigitalLibrary/NewArtwork")
    public ModelAndView newArtworkPage(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("User");
        }
        if (user.isManager() || user.isAdministrator()){
            Author auth = new Author();
            Category cat = new Category();
            List<Author> authorList = auth.getAuthorList();
            List<Category> categoryList = cat.getCategoryList();
            ModelAndView mav = new ModelAndView("NewArtwork");
            mav.addObject("categoryList", categoryList);
            mav.addObject("authorList", authorList);
            return mav;
        }
        else {
            return new ModelAndView( "redirect:/DigitalLibrary");
        }
    }

    @RequestMapping("DigitalLibrary/MyTranscriptions")
    public ModelAndView myTranscriptions(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("User");
        }
        if (user.isTranscriber() || user.isAdministrator()){
            ResultSet rs = user.getTranscriptionList(user.getId());
            TreeMap<Integer, String> write_associations = new TreeMap<>();
            while (rs.next()){
                write_associations.put(rs.getInt("art_id"), rs.getString("title") + " Numero pagine dell'opera assegnate: <strong>" + rs.getString("numero_pagine") + "</strong>");
            }
            ModelAndView mav = new ModelAndView("AssignedTranscriptions");
            mav.addObject("assigned", write_associations);
            return mav;
        }
        else {
            return new ModelAndView( "redirect:/DigitalLibrary");
        }
    }
}