package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Meta;
import services.MetaService;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(urlPatterns = "meta")
public class MetaServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Meta meta = new Meta(MetaService.getVisitNumber(),MetaService.getTime());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(meta);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}