package com.univaq.oosde.controller;

import com.univaq.oosde.entity.ConnectionClass;
import com.univaq.oosde.entity.artwork;
import com.univaq.oosde.entity.user;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
    @RequestMapping("/oosde")
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
            user user = new user(resultSet);
            session.setAttribute("User", user);
        }
        //FIN QUI//
        artwork a = new artwork();
        List<artwork> artworks = a.getNameIdAllOperas();
        ModelAndView mav = new ModelAndView("DigitalLibrary");
        mav.addObject("artworks", artworks);
        return mav;
        //DA AGGIUNGERE//
        //}
    }
}


