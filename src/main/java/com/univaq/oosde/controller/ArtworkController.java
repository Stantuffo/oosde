package com.univaq.oosde.controller;

import com.univaq.oosde.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.TreeMap;

@Controller
public class ArtworkController {

    @GetMapping("DigitalLibrary/Artwork")
    public ModelAndView viewArtworkPage(@RequestParam int artId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("User") == null) {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        Artwork art = Artwork.getArtworkById(artId);
        List<Image> imgs = art.getArtworkPages(artId);
        List<Author> authorList = Artwork.getAuthorListById(artId);
        ModelAndView mav = new ModelAndView("ArtworkOverview");
        String catName = Category.getCategoryNameById(art.getCat_id());
        mav.addObject("catName", catName);
        mav.addObject("artwork", art);
        mav.addObject("authorList", authorList);
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
        if (user.isManager() || user.isAdministrator()) {
            Author auth = new Author();
            Category cat = new Category();
            List<Author> authorList = auth.getAuthorList();
            List<Category> categoryList = cat.getCategoryList();
            ModelAndView mav = new ModelAndView("NewArtwork");
            mav.addObject("categoryList", categoryList);
            mav.addObject("authorList", authorList);
            return mav;
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }

    @RequestMapping("DigitalLibrary/MyTranscriptions")
    public ModelAndView myTranscriptions(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("User");
        }
        if (user.isTranscriber() || user.isAdministrator()) {
            ResultSet rs = user.getTranscriptionList(user.getId());
            TreeMap<Integer, String> write_associations = new TreeMap<>();
            while (rs.next()) {
                write_associations.put(rs.getInt("art_id"), rs.getString("title") + " Numero pagine dell'opera assegnate: <strong>" + rs.getString("numero_pagine") + "</strong>");
            }
            ModelAndView mav = new ModelAndView("AssignedTranscriptions");
            mav.addObject("assigned", write_associations);
            return mav;
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }

    @GetMapping("DigitalLibrary/MyTranscriptions/Artwork")
    public ModelAndView myArtworkTranscriptions(@RequestParam int artId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("User");
        }
        if (user.isTranscriber() || user.isAdministrator()) {
            TreeMap<Integer, String> imageList = user.getTranscriptionListByArtId(artId, user.getId());
            ModelAndView mav = new ModelAndView("AssignedTranscriptionsList");
            mav.addObject("ArtworkId", artId);
            mav.addObject("transcriptionList", imageList);
            return mav;
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }

    @RequestMapping(value = "/DigitalLibrary/AddArtwork", method = RequestMethod.POST)
    public ModelAndView insertNewArtwork(@RequestParam("inputTitle") String title, @RequestParam("inputDescription") String description, @RequestParam("inputIsbn") String isbn, @RequestParam("inputYear") int year, @RequestParam("inputLanguage") String language, @RequestParam("inputCategory") int category, @RequestParam("inputAuthors") String[] author, HttpServletRequest request) throws SQLException {
        String[] a = author;
        HttpSession session = request.getSession(true);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("User");
        }
        if (!user.isManager() || !user.isAdministrator()) {
            return new ModelAndView("redirect:/DigitalLibrary");
        } else {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            if (title.equals("")) {
                return new ModelAndView("redirect:/DigitalLibrary/Newrtwork");
            } else {
                String sql = "INSERT INTO artwork(title, description, language, year, cat_id, added_by, isbn, published) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, title);
                statement.setString(2, description);
                statement.setString(3, language);
                if (year > 0) {
                    statement.setInt(4, year);
                } else {
                    statement.setNull(4, Types.INTEGER);
                }
                if (category > 0) {
                    statement.setInt(5, category);
                } else {
                    statement.setNull(5, Types.INTEGER);
                }
                statement.setInt(6, user.getId());
                if (!isbn.equals("")) {
                    statement.setString(7, isbn);
                } else {
                    statement.setNull(7, Types.VARCHAR);
                }
                statement.setInt(8, 0);
                statement.executeUpdate();

                sql = "SELECT art_id FROM artwork ORDER BY art_id DESC LIMIT 1";
                ResultSet rs = statement.executeQuery(sql);
                rs.next();
                int art_id = rs.getInt("art_id");
                for (int i = 0; i < author.length; i++) {
                    sql = "INSERT INTO paternity(author_id, artwork_id) VALUES (?, ?)";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, Integer.parseInt(author[i]));
                    statement.setInt(2, art_id);
                    statement.executeUpdate();
                }
                boolean success = (new File("C:/Users/simon/Documents/oosde/src/main/resources/static/" + art_id)).mkdirs();
                return new ModelAndView("ArtworkAdded");
            }
        }
    }

    @RequestMapping(value = "/DigitalLibrary/AssignTranscription")
    public ModelAndView assignTranscription(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("User");
        }
        if (user.isManager() || user.isAdministrator()) {
            List<Artwork> artworkList = Artwork.getArtworkWithNotValidatedImages();
            ModelAndView mav = new ModelAndView("ChooseArtworkForTranscription");
            mav.addObject("artworkList", artworkList);
            return mav;
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }

    @GetMapping(value = "/DigitalLibrary/AssignTranscription/ChooseTranscriber")
    public ModelAndView chooseTranscriber(@RequestParam int artId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("User");
        }
        if (user.isManager() || user.isAdministrator()) {
            List<Image> imageList = Image.getNotValidatedImages(artId);
            List<User> transcriberList = User.getTranscribers();
            ModelAndView mav = new ModelAndView("ChooseTranscriber");
            mav.addObject("artworkId", artId);
            mav.addObject("imageList", imageList);
            mav.addObject("transcriberList", transcriberList);
            return mav;
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }


    @PostMapping(value = "/DigitalLibrary/AssignTranscription/AssignResult")
    public ModelAndView insertAssignment(@RequestParam("inputUser") int inputUser,
                                         @RequestParam("inputImageId") int[] inputImageId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("User");
        }
        if (user.isManager() || user.isAdministrator()) {
            int count = 0;
            for (int imgid : inputImageId) {
                boolean esiste = User.checkIfUserHasPageYet(inputUser, imgid);
                if (!esiste) {
                    User.assignTranscriptionToUser(inputUser, imgid);
                }
                count++;
            }
            User usr = User.getUserById(inputUser);
            ModelAndView mav = new ModelAndView("TranscriptionsAssigned");
            mav.addObject("count", count);
            mav.addObject("user", usr);
            return mav;
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }

    @RequestMapping(value = "/DigitalLibrary/Artwork/ValidateTranscription")
    public ModelAndView getTranscriptionsToBeValidated(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("User");
        }
        if (user.isManager() || user.isAdministrator()) {
            List<Image> pages = Image.getTranscriptionsToValidate();
            ModelAndView mav = new ModelAndView("UnvalidatedTranscriptionList");
            mav.addObject(pages);
            return mav;
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }
}