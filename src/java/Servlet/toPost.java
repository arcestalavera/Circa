/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Classes.Event;
import Classes.User;
import Database.CircaDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arces
 */
public class toPost extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PostToEvent</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PostToEvent at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String action = request.getParameter("action");
        RequestDispatcher reqDispatcher = null;
        Event event = null;
        CircaDatabase db = CircaDatabase.getInstance();
        int postID;
        String postText;

        switch (action) {
            case "post":
                event = (Event) request.getSession().getAttribute("eventDetails");
                User user = (User) request.getSession().getAttribute("loggedUser");
                postText = request.getParameter("postText");

                db.addPost(event.getEventID(), user.getUserID(), postText);

                break;
            case "delete":
                postID = Integer.parseInt(request.getParameter("id"));
                event = (Event) request.getSession().getAttribute("eventDetails");

                db.deletePost(postID);

                break;
            case "edit":
                postID = Integer.parseInt(request.getParameter("id"));
                postText = request.getParameter("postEditText");
                event = (Event) request.getSession().getAttribute("eventDetails");

                db.editPost(postID, postText);
                break;
        }
        reqDispatcher = request.getRequestDispatcher("Event?action=view&id=" + event.getEventID());
        reqDispatcher.forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
