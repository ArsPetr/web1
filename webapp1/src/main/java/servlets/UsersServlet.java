package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.User;
import services.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

@WebServlet(urlPatterns = "users/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5,
        location = "C:\\Users\\Пользователь\\IdeaProjects\\webapp1\\src\\main\\webapp\\static\\images")
public class UsersServlet extends HttpServlet {

    private final String PROFILE_PIC_PATH = "/static/images/";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<User> users = UserService.getUsers();

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(users);

        out.print(json);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getContentType() == "application/json") {

            ObjectMapper objectMapper = new ObjectMapper();
            String body = inputStreamToString(req.getInputStream());
            System.out.println(body);


            User user = objectMapper.readValue(body, User.class);
            System.out.println(user.getId());
            UserService.updateUser(user);
        }else {
            for (Part part : req.getParts()) {
                String fileName = UUID.randomUUID().toString() + part.getSubmittedFileName();

                part.write(fileName);

                User user = new User();
                user.setProfilePic(PROFILE_PIC_PATH + fileName);
                user.setLogin(req.getPathInfo().substring(1));

                UserService.updateUser(user);
            }
        }
    }

    private static String inputStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        return scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
    }

}
