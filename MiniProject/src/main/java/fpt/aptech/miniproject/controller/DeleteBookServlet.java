/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fpt.aptech.miniproject.controller;

import fpt.aptech.miniproject.dao.BookDao;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/**
 *
 * @author Admin
 */
public class DeleteBookServlet extends HttpServlet {
    
    BookDao dao;
    
    public DeleteBookServlet() {
        dao = new BookDao();
    }
    
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if (null == action) {
                req.setAttribute("list", dao.findAll());
                req.getRequestDispatcher("Books/index.jsp").forward(req, resp);
            } else {
                switch (action) {
                    case "Delete":
                        for (String object : req.getParameterValues("del")) {
                            int nId = Integer.parseInt(object);
                            dao.deleteObject(nId);
                        }
                        resp.sendRedirect("DeleteBookServlet");
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
        
    }
    
}
