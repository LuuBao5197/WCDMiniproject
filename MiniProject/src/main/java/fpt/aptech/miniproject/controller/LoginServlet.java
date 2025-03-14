package fpt.aptech.miniproject.controller;

import fpt.aptech.miniproject.models.Roles;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @PersistenceContext(unitName = "miniProject")
    private EntityManager em;
    private EntityManagerFactory emf;

    // This method handles GET requests (e.g., showing the login page)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to the login page (or just forward to a JSP)
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    // This method handles POST requests (e.g., handling the login form submission)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("j_username");
        String email = request.getParameter("j_email");

        // Log the received form values
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);

        try {
            emf = Persistence.createEntityManagerFactory("miniProject");
            em = emf.createEntityManager();

            int roleId = getUserRoleFromDatabase(username, email);

            // Log the roleId value
            System.out.println("Role ID: " + roleId);

            if (roleId == 3) {
                // Save user info in the session
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("roleId", roleId);

                // Redirect to the publisher page
                response.sendRedirect("/Publishers/index.jsp");
            } else {
                response.sendRedirect("error.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");  // Redirect to error page if something goes wrong
        }
    }

    private int getUserRoleFromDatabase(String username, String email) {
        try {
            String query = "SELECT u.roleId FROM Users u WHERE u.username = :username AND u.email = :email";
            Roles role = (Roles) em.createQuery(query)
                    .setParameter("username", username)
                    .setParameter("email", email)
                    .getSingleResult();  // Return the Roles object

            if (role != null) {
                return role.getRoleId();  // Return roleId from the Roles object
            } else {
                System.out.println("Role is null for the provided username and email.");
                return -1;  // Return -1 if role not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;  // Return -1 if error occurs
        }
    }
}
