package servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import services.UserService;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(urlPatterns = "usersession")
public class UserSessionServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserService.getUserByLogin(req.getSession().getAttribute("login").toString());
        user.setPassword(null);
        //user.setProfilePic(req.getSession().getAttribute("").toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
}
