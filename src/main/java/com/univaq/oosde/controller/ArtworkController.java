package com.univaq.oosde.controller;

import com.univaq.oosde.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.List;
import java.util.TreeMap;

/**
 * @author  Simone;
 * @version 1
 **/
@Controller
public class ArtworkController {


    /**
     * @param artId         The id of the Artwork to be shown.
     * @param request       HttpServletRequest.
     * @return              If logged, the ModelAndView containing all the Artwork data and the template page to be rendered; if not logged, a redirect to the Login Page.
     * @throws SQLException Thrown if data manipulation error arises.
     */
    @GetMapping("DigitalLibrary/Artwork")
    public ModelAndView viewArtworkPage(@RequestParam int artId, HttpServletRequest request) throws SQLException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        Artwork art = Artwork.getArtworkById(artId);
        List<Image> imgs = art.getArtworkPages(artId);
        List<Author> authorList = Artwork.getAuthorListById(artId);
        ModelAndView mav = new ModelAndView("ArtworkOverview");
        String catName = Category.getCategoryNameById(art.getCat_id());
        assert catName != null;
        mav.addObject("catName", catName);
        mav.addObject("artwork", art);
        mav.addObject("authorList", authorList);
        mav.addObject("pages", imgs);
        return mav;
    }

    /**
     * @param request       HttpServletRequest.
     * @return              If logged, the ModelAndView containing all the Category List data,the Author and the template page to be rendered; if not logged, a redirect to the Login Page.
     * @throws SQLException Thrown if data manipulation error arises.
     */
    @RequestMapping("DigitalLibrary/NewArtwork")
    public ModelAndView newArtworkPage(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        assert user != null;
        if (user.isManager() || user.isAdministrator()) {
            Author auth = new Author();
            Category cat = new Category();
            List<Author> authorList = auth.getAuthorList();
            List<Category> categoryList = cat.getCategoryList();
            ModelAndView mav = new ModelAndView("NewArtwork");
            mav.addObject("categoryList", categoryList);
            mav.addObject("authorList", authorList);
            return mav;
        } else return new ModelAndView("redirect:/DigitalLibrary");
    }

    /**
     * @param request       HttpServletRequest.
     * @return              If logged, the ModelAndView containing all the Assigned Transcription data and the template page to be rendered; if not logged, a redirect to the Login Page.
     * @throws SQLException Thrown if data manipulation error arises.
     */
    @RequestMapping("DigitalLibrary/MyTranscriptions")
    public ModelAndView myTranscriptions(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        assert user != null;
        if (user.isTranscriber() || user.isAdministrator()) {
            ResultSet rs = user.getTranscriptionList(user.getId());
            TreeMap<Integer, String> write_associations = new TreeMap<>();
            while (rs.next()) {
                write_associations.put(rs.getInt("art_id"), rs.getString("title") + " Numero pagine dell'opera assegnate: <strong>" + rs.getString("numero_pagine") + "</strong>");
            }
            ModelAndView mav = new ModelAndView("AssignedTranscriptions");
            mav.addObject("assigned", write_associations);
            return mav;
        } else return new ModelAndView("redirect:/DigitalLibrary");
    }

    /**
     * @param artId             The id of the Artwork to be shown.
     * @param request           HttpServletRequest.
     * @return                  If logged, the ModelAndView containing all the Artwork data and the template page to be rendered; if not logged, a redirect to the Login Page.
     * @throws SQLException     thrown if data manipulation error arises.
     */
    @GetMapping("DigitalLibrary/MyTranscriptions/Artwork")
    public ModelAndView myArtworkTranscriptions(@RequestParam int artId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        assert user != null;
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

    /**
     * @param title         The title of the Artwork to be added.
     * @param description   The description of the Artwork to be added.
     * @param isbn          The title of the Artwork to be added.
     * @param year          The title of the Artwork to be added.
     * @param language      The title of the Artwork to be added.
     * @param category      The title of the Artwork to be added.
     * @param author        The title of the Artwork to be added.
     * @param request       HttpServletRequest.
     * @return              If logged and if the insertion is successful, a redirect to a success page, if the insertion is not successful a redirect to an error page; if not logged, a redirect to the Login Page.
     * @throws SQLException Thrown if data manipulation error arises.
     */
    @RequestMapping(value = "/DigitalLibrary/AddArtwork", method = RequestMethod.POST)
    public ModelAndView insertNewArtwork(@RequestParam("inputTitle") String title, @RequestParam("inputDescription") String description, @RequestParam("inputIsbn") String isbn, @RequestParam("inputYear") int year, @RequestParam("inputLanguage") String language, @RequestParam("inputCategory") int category, @RequestParam("inputAuthors") String[] author, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        assert user != null;
        if (!user.isManager() && !user.isAdministrator()) {
            return new ModelAndView("redirect:/DigitalLibrary");
        } else {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            if (title.equals("")) {
                return new ModelAndView("redirect:/DigitalLibrary/NewArtwork");
            } else {
                int usrId = user.getId();
                int art_id = Artwork.addArtwork(connection, title, description, language, year, category, usrId, isbn);
                boolean success = Artwork.addAuthors(art_id, author, connection);
                if (success) {
                    (new File("C:/Users/simon/Documents/oosde/src/main/resources/static/" + art_id)).mkdirs();
                    return new ModelAndView("ArtworkAdded");
                } else return new ModelAndView("ErrorInsertingArtwork");
            }
        }
    }

    /**
     * @param request           HttpServletRequest.
     * @return                  If logged, the ModelAndView containing all the data and the page allowing a trascriber to choose the Artwork to transcribe; if not logged, a redirect to the Login Page.
     * @throws SQLException     Thrown if data manipulation error arises.
     */
    @RequestMapping(value = "/DigitalLibrary/AssignTranscription")
    public ModelAndView assignTranscription(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        assert user != null;
        if (user.isManager() || user.isAdministrator()) {
            List<Artwork> artworkList = Artwork.getArtworkWithNotValidatedImages();
            ModelAndView mav = new ModelAndView("ChooseArtworkForTranscription");
            mav.addObject("artworkList", artworkList);
            return mav;
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }

    /**
     * @param artId             The id of the Artwork to be shown.
     * @param request           HttpServletRequest.
     * @return                  If logged, the ModelAndView containing all the Images of a specific Artwork, a list of all the transcribers and the template page to be rendered; if not logged, a redirect to the Login Page.
     * @throws SQLException     Thrown if data manipulation error arises.
     */
    @GetMapping(value = "/DigitalLibrary/AssignTranscription/ChooseTranscriber")
    public ModelAndView chooseTranscriber(@RequestParam int artId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
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

    /**
     * @param inputUser         The id of the Transcriber.
     * @param inputUser         The id list of all the images assigne to the Transcriber.
     * @param request           HttpServletRequest.
     * @return                  If logged, a success page after the assignment of all the Images to the selected Transcriber and the template page itself; if not logged, a redirect to the Login Page.
     * @throws SQLException     Thrown if data manipulation error arises.
     */
    @PostMapping(value = "/DigitalLibrary/AssignTranscription/AssignResult")
    public ModelAndView insertAssignment(@RequestParam("inputUser") int inputUser,
                                         @RequestParam("inputImageId") int[] inputImageId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        assert user != null;
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

    /**
     * @param request       HttpServletRequest.
     * @return              If logged, the ModelAndView containing a list of unvalidated transcriptions willing to be validated and the template page to be rendered; if not logged, a redirect to the Login Page.
     * @throws SQLException Thrown if data manipulation error arises.
     */
    @RequestMapping(value = "/DigitalLibrary/Artwork/ValidateTranscription")
    public ModelAndView getTranscriptionsToBeValidated(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        assert user != null;
        if (user.isManager() || user.isAdministrator()) {
            List<Image> pages = Image.getTranscriptionsToValidate();
            ModelAndView mav = new ModelAndView("UnvalidatedTranscriptionList");
            mav.addObject("unvalTranscriptions", pages);
            return mav;
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }

    /**
     * @param pageId        The id of the page whose transcription is being validating.
     * @param request       HttpServletRequest.
     * @return              If logged, a page where Administrators and Manager can review and validate the transcription; if not logged, a redirect to the Login Page.
     * @throws SQLException Thrown if data manipulation error arises.
     */
    @GetMapping(value = "/DigitalLibrary/Artwork/ValidateTranscription/Validate")
    public ModelAndView validateTr(@RequestParam("pageId") int pageId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        Image img = Image.getImageById(pageId);
        ModelAndView mav = new ModelAndView("TranscriptionValidation");
        assert img != null;
        mav.addObject("img", img);
        return mav;
    }

    /**
     * @param imgId         The id of the Image associated to the transcriptioin to validate.
     * @param transcription The text of the transcription itself.
     * @param request       HttpServletRequest.
     * @return              A redirect to the main page
     * @throws SQLException Thrown if data manipulation error arises.
     */
    @PostMapping("/DigitalLibrary/ValidateTranscription")
    public String validateTranscription(@RequestParam("imgId") int imgId, @RequestParam("transcription") String transcription, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Image.validateTranscription(imgId, transcription);
        }
        return "redirect:/DigitalLibrary";
    }

    /**
     * @param request       HttpServletRequest.
     * @return              If logged, the ModelAndView containing a list of unvalidated Images willing to be validated and the template page to be rendered; if not logged, a redirect to the Login Page.
     * @throws SQLException Thrown if data manipulation error arises.
     */
    @RequestMapping(value = "/DigitalLibrary/Artwork/ValidateImage")
    public ModelAndView getImagesToBeValidated(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        assert user != null;
        if (user.isManager() || user.isAdministrator()) {
            List<Image> pages = Image.getImagesToValidate();
            ModelAndView mav = new ModelAndView("UnvalidatedImageList");
            mav.addObject("unvalImages", pages);
            return mav;
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
    }

    /**
     * @param imgId             The id of the Image to be validated.
     * @param request           HttpServletRequest.
     * @return                  If logged, the ModelAndView containing the image to be validated and all its attributes and the template page to be rendered; if not logged, a redirect to the Login Page.
     * @throws SQLException     Thrown if data manipulation error arises.
     * @throws IOException      Thrown if IO error arises.
     * @throws ParseException   Thrown if parsing error arises.
     */
    @GetMapping(value = "/DigitalLibrary/Artwork/ValidateImage/Validate")
    public ModelAndView getImagesToBeValidated(@RequestParam("imgId") int imgId, HttpServletRequest request) throws SQLException, IOException, ParseException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        Image img = Image.getImageById(imgId);
        assert img != null;
        Artwork art = Artwork.getArtworkById(img.getArtwork_id());
        ModelAndView mav = new ModelAndView("ImageValidation");
        mav.addObject("img", img);
        mav.addObject("art", art);
        mav.addObject("size", Image.getImageSize(art.getId(), img.getImg_url()));
        mav.addObject("width", Image.getImageWidth(art.getId(), img.getImg_url()));
        mav.addObject("height", Image.getImageHeight(art.getId(), img.getImg_url()));
        return mav;
    }

    /**
     * @param imgId         The id of the image to be finally validated.
     * @param request       HttpServletRequest.
     * @return              A redirect to the main page.
     * @throws SQLException Thrown if parsing error arises.
     */
    @PostMapping("/DigitalLibrary/ValidateImage")
    public String validateImage(@RequestParam("imgId") int imgId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Image.validateImage(imgId);
        }
        return "redirect:/DigitalLibrary";
    }
}