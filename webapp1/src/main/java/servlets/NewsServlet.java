package servlets;

import services.NewsDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.News;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

@WebServlet(urlPatterns = "/news")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5,
        location = "F:\\projects\\web1\\webapp1\\src\\main\\webapp\\static\\images")
public class NewsServlet extends HttpServlet {
    private final String PIC_PATH = "/static/images/";
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
        ArrayList<News> newsArrayList = NewsDAO.getNews();

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(newsArrayList);

        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        News news = null;
        String fileName = null;
        for (Part part : req.getParts()){
            if (Objects.equals(part.getName(), "image")){
                fileName = UUID.randomUUID().toString() + part.getSubmittedFileName();
                part.write(fileName);
            }
            if (Objects.equals(part.getName(), "inputs")){
                ObjectMapper objectMapper = new ObjectMapper();
                String body = inputStreamToString(part.getInputStream());

                news = objectMapper.readValue(body, News.class);

            }
        }

        news.setImage(PIC_PATH + fileName);
        NewsDAO.insert(news);
    }
    private static String inputStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        return scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
    }
}
