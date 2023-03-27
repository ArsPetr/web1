package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import services.UserService;

import java.io.IOException;
@WebServlet(urlPatterns = "login")
public class LoginServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/public/loginPage.html").forward(req,resp);
        log("1");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        System.out.println("L:"+login+" P: "+password);
        User user = new User(login,password);
        HttpSession session = req.getSession();

        if(UserService.isAunthentificated(user)) {
            session.setAttribute("login",login);
            session.setAttribute("password",password);
            session.setAttribute("role",UserService.getUserRole(login));
            resp.sendRedirect("main");
        }
        else resp.sendRedirect("login");


    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
