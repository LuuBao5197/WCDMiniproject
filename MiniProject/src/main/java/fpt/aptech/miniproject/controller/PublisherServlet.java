/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fpt.aptech.miniproject.controller;

import fpt.aptech.miniproject.models.Books;
import fpt.aptech.miniproject.models.RatingsBook;
import fpt.aptech.miniproject.models.Users;
import fpt.aptech.miniproject.models.dao.BookDAO;
import fpt.aptech.miniproject.ultis.FileUltis;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Luu Bao
 */
@MultipartConfig(maxFileSize = 1617211)
public class PublisherServlet extends HttpServlet {

    BookDAO dao;

    public PublisherServlet() {
        dao = new BookDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            HttpSession session = request.getSession(false); // Không tạo session mới nếu chưa có
            Users u = (session != null) ? (Users) session.getAttribute("uLogin") : null;
            if(u == null){
                response.sendRedirect("/login.jsp");
                return;
            }
            if (null == action) {
                List<Books> bList = dao.getBooks();
                request.setAttribute("list", bList);
                request.setAttribute("page", "index.jsp");
                request.getRequestDispatcher("header.jsp").forward(request, response);
            } else {
                switch (action) {
                    case "Upload":
                        List<Books> bList = dao.getBooks();
                        request.setAttribute("list", bList);
                        request.setAttribute("page", "uploadBook.jsp");
                        request.getRequestDispatcher("header.jsp").forward(request, response);
                        break;
                    case "Rating":
                        List<RatingsBook> rList = dao.getRatingBooks();
                        request.setAttribute("rList", rList);
                        request.setAttribute("page", "ratingList.jsp");
                        request.getRequestDispatcher("header.jsp").forward(request, response);
                        break;
                    case "Feedback":
                        int bookId = Integer.parseInt(request.getParameter("bookId"));
                        request.setAttribute("fList", dao.getReviewBookById(bookId));
                        request.setAttribute("page", "feedbackList.jsp");
                        request.getRequestDispatcher("header.jsp").forward(request, response);
                        break;
                    case "Create":
                        String title = request.getParameter("txtTitle");
                        String genre = request.getParameter("txtGenre");
                        String author = request.getParameter("txtAuthor");
                        String edition = request.getParameter("txtEdition");
                        int nEdition = Integer.parseInt(edition);
                        String userId = request.getParameter("txtPublisher");
                        int nUserId = Integer.parseInt(userId);
                        Part p = request.getPart("file");

                        if (p != null) {
                            String fileName = p.getSubmittedFileName();
                            String uniqueFileName = FileUltis.generateUniqueFileName(fileName);
                            Books b = new Books(title, author, nEdition, genre, uniqueFileName);
                            int row = dao.saveBook(b, nUserId);
                            if (row == 1) {
                                // Luu file vao thu muc 
                                try {
                                    String dir = "D:/WCD/WCDMiniproject/MiniProject/src/main/webapp/images/" + uniqueFileName;
                                    InputStream is;
                                    FileOutputStream os = new FileOutputStream(dir);
                                    is = p.getInputStream();
                                    byte[] data = new byte[is.available()];
                                    is.read(data);
                                    os.write(data);

                                    is.close();
                                    response.sendRedirect("PublisherServlet");
                                    return;

                                } catch (IOException e) {
                                    System.out.println(e.getMessage());
                                }

                            } else {

                            }
//                            response.sendRedirect("PublisherServlet");

                        }
                        break;
                    case "Delete":
                        for (String object : request.getParameterValues("del")) {
                            int nId = Integer.parseInt(object);
                            dao.deleteObject(nId);
                        }
                        response.sendRedirect("PublisherServlet");
                        
                        break;
                    default:
                        throw new AssertionError();

                }
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

}
