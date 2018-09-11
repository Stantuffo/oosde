package com.univaq.oosde.controller;

import com.univaq.oosde.model.Artwork;
import com.univaq.oosde.model.Image;
import com.univaq.oosde.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ImageController {

    @GetMapping("DigitalLibrary/Artwork/ImageDetail")
    public ModelAndView viewArtworkPage(@RequestParam int imgId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        Image img = Image.getImageById(imgId);
        ModelAndView mav = new ModelAndView("ImageDetail");
        assert img != null;
        mav.addObject("img", img);
        return mav;
    }

    @GetMapping("DigitalLibrary/MyTranscriptions/Artwork/Image")
    public ModelAndView Transcription(@RequestParam int imgId, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        User user;
        if (session != null) {
            user = (User) session.getAttribute("User");
        } else {
            return new ModelAndView("redirect:/DigitalLibrary");
        }
        if (user == null) {
            return new ModelAndView("redirect:/DigitalLibrary");
        } else {
            Image img = Image.getImageById(imgId);
            ModelAndView mav = new ModelAndView("TranscriptionView");
            mav.addObject("img", img);
            return mav;
        }
    }

    @PostMapping("DigitalLibrary/AddTranscription")
    public String insertTranscription(@RequestParam String transcription, int imgId) throws SQLException {
        boolean esito = Image.insertTrascrizione(imgId, transcription);
        if (esito) return "redirect:/DigitalLibrary/TranscriptionResult?idImg=" + imgId;
        else return "redirect:/DigitalLibrary/TranscriptionResult";
    }

    @GetMapping("DigitalLibrary/TranscriptionResult")
    public ModelAndView showResults(@RequestParam("idImg") int idImg) throws SQLException {
        String transcription;
        transcription = Image.getTranscriptionByImgId(idImg);
        ModelAndView mav = new ModelAndView("TranscriptionResult");
        mav.addObject("transcription", transcription);
        return mav;
    }

    @RequestMapping("DigitalLibrary/NewImage")
    public ModelAndView addImage() throws SQLException {
        List<Artwork> artworkList = Artwork.getAllArtworks(true);
        ModelAndView mav = new ModelAndView("NewImage");
        mav.addObject("artworkList", artworkList);
        return mav;
    }

    @RequestMapping("DigitalLibrary/AddImage")
    public String uploadImages(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        // Create path components to save the file
        final int path = Integer.parseInt(request.getParameter("inputArtwork"));
        final Part filePart = request.getPart("file-5");
        final String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;
        //final PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File("C:/Users/simon/Documents/oosde/src/main/resources/static/" + path + File.separator + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            Image.insertImage(path, fileName);
            //writer.println("New file " + fileName + " created at " + path);
        } catch (FileNotFoundException fne) {
            //writer.println("You either did not specify a file to upload or are " + "trying to upload a file to a protected or nonexistent " + "location.");
            //writer.println("<br/> ERROR: " + fne.getMessage());

        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
        return "redirect:/DigitalLibrary/AddImage/ImageAdded";
    }

    @RequestMapping("DigitalLibrary/AddImage/ImageAdded")
    public ModelAndView imageAdded() {
        return new ModelAndView("ImageAdded");
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}


