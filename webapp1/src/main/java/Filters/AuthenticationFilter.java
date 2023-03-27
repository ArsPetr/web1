package Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;


@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req =(HttpServletRequest) servletRequest;
        HttpServletResponse resp =(HttpServletResponse) servletResponse;

        resp.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
        HttpSession session = req.getSession();
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (session.getAttribute("login") == null ||
                session.getAttribute("password") == null ||
                session.getAttribute("role") == null ) {

            if (path.endsWith("/login") || path.startsWith("/registration") || path.startsWith("/images")) {
                filterChain.doFilter(req, resp);
                return;
            }
            System.out.println("Y");
            resp.sendRedirect("/login");
        }
        else {
            if (path.startsWith("/login") || path.startsWith("/registration")) {
                resp.sendRedirect("/main");
                return;
            }
            filterChain.doFilter(req,resp);
        }
    }

    @Override
    public void destroy() {

    }
}
