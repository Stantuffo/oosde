package com.univaq.oosde.controller;

import com.univaq.oosde.model.ConnectionClass;
import com.univaq.oosde.model.Artwork;
import com.univaq.oosde.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        //DA AGGIUNGERE//
        /*HttpSession session = request.getSession();
        if (session.getAttribute("User") == null || session.getAttribute("User").equals("")) {
            return new ModelAndView("LoginPage");
        } else {*/
        //FIN QUI//

        //DA TOGLIERE//
        String sql = "SELECT * FROM user WHERE usr_id = 1";
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        HttpSession session = request.getSession(true);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            User user = new User(resultSet);
            session.setAttribute("User", user);
        }
        //FIN QUI//
        User user = (User) session.getAttribute("User");
        Artwork a = new Artwork();
        boolean flag = (user.isAdministrator() || user.isManager());
        List<Artwork> artworks = a.getAllOperas(flag);
        ModelAndView mav = new ModelAndView("DigitalLibrary");
        mav.addObject("artworks", artworks);
        return mav;
        //DA AGGIUNGERE//
        //}
    }
}


