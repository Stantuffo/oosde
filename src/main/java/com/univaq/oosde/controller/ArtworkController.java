package com.univaq.oosde.controller;

import com.univaq.oosde.entity.artwork;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.sql.SQLException;

@Controller
public class ArtworkController {

    @GetMapping("oosde/DigitalLibrary/opera")
    public ModelAndView clusters(@RequestParam int id) throws SQLException {
        artwork a = artwork.getArtworkById(id);
        ModelAndView mav = new ModelAndView("ArtworkOverview");
        mav.addObject("artwork", a);
        return mav;
    }


}