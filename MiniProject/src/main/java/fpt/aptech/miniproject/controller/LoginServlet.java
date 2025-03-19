package fpt.aptech.miniproject.controller;

import fpt.aptech.miniproject.models.Publishers;
import fpt.aptech.miniproject.models.Roles;
import fpt.aptech.miniproject.models.Users;
import fpt.aptech.miniproject.models.dao.BookDAO;
import fpt.aptech.miniproject.models.dao.UsesDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    UsesDAO dao;
    BookDAO bdao;

    public LoginServlet() {
        dao = new UsesDAO();
        bdao = new BookDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        try {
            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Username and Password cannot be empty!");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            Users user = dao.checkLogin(username, password);

            if (user == null) {
                request.setAttribute("errorMessage", "Invalid username or password!");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                if ("Publisher".equals(user.getRoleId().getRoleName())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("uLogin", user);
                    Publishers p = bdao.findOnePublisher(user.getUserId());
                    session.setAttribute("publisher", p);
                    response.sendRedirect("PublisherServlet");
                }
            }
        } catch (ServletException | IOException e) {
            System.out.println(e.getMessage());
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }


    }
}
