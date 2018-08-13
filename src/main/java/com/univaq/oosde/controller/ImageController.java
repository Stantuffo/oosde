package com.univaq.oosde.controller;

import com.univaq.oosde.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Controller
public class ImageController {

    @GetMapping("DigitalLibrary/Artwork/ImageDetail")
    public ModelAndView viewArtworkPage(@RequestParam int imgId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("User");
        if (user == null) {
            return new ModelAndView("redirect: ../../../../DigitalLibrary");
        }
        else {
            Image img = Image.getImageById(imgId);
            ModelAndView mav = new ModelAndView("ImageDetail");
            mav.addObject("img", img);
            return mav;
        }
    }

    @GetMapping("DigitalLibrary/MyTranscriptions/Artwork/Image")
    public ModelAndView Transcription(@RequestParam int imgId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("User");
        if (user == null) {
            return new ModelAndView("redirect: ../../../../DigitalLibrary");
        }
        else {
            Image img = Image.getImageById(imgId);
            ModelAndView mav = new ModelAndView("TranscriptionView");
            mav.addObject("img", img);
            return mav;
        }
    }
}


