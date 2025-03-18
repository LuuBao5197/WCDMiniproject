package fpt.aptech.miniproject.controller;

import fpt.aptech.miniproject.models.Roles;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @PersistenceContext(unitName = "miniProject")
    private EntityManager em;
    private EntityManagerFactory emf;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username and Password cannot be empty!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            emf = Persistence.createEntityManagerFactory("miniProject");
            em = emf.createEntityManager();

            Roles role = getUserRoleFromDatabase(username, password);

            if (role == null) {
                request.setAttribute("errorMessage", "Invalid username or password!");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("roleId", role);  
                response.sendRedirect("/Publishers/index.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private Roles getUserRoleFromDatabase(@Valid String username, String password) {
        try {
            String query = "SELECT u.roleId FROM Users u WHERE u.username = :username AND u.password = :password";

            Roles role = (Roles) em.createQuery(query)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();

            if (role != null) {
                return role;  
            } else {
                System.out.println("No role found for the provided username and password.");
                return null;  
            }
        } catch (NoResultException e) {
            System.out.println("Can't find account: " + username);
            return null;  
        } catch (Exception e) {
            e.printStackTrace();
            return null;  
        }
    }
}
